(ns ring.middleware.mdc-test
  (:require [clojure.test :refer [deftest is use-fixtures]]
            [ring.middleware.mdc :refer [wrap-mdc wrap-clear-mdc]]
            [ring.mock.request :as mock])
  (:import org.slf4j.MDC))

;; -----------------------------------------------------------------------------
;; fixtures

(defn clear-mdc-fixture [f]
  (f)
  (MDC/clear))

(use-fixtures :each
  clear-mdc-fixture)

;; -----------------------------------------------------------------------------
;; helpers

(defn select-headers [request header-names]
  (select-keys (request :headers) header-names))

(defn req->mdc [req]
  (select-headers req #{"x-session-id"}))

(defn build-mdc-echo-handler [field]
  (fn [request]
    (MDC/put "other" "value")
    {:status 200
     :headers {}
     :body (MDC/get field)}))

(defn mock-request []
  (-> (mock/request :get "/")
      (mock/header "x-session-id" "1234")))

;; -----------------------------------------------------------------------------
;; tests

(deftest test-wrap-mdc-sets-data
  (let [handler (wrap-mdc (build-mdc-echo-handler "x-session-id") req->mdc)
        request (mock-request)
        response (handler request)]
    (is (= "1234" (response :body)))))

(deftest test-wrap-mdc-clears-its-data
  (let [handler (wrap-mdc (build-mdc-echo-handler "x-session-id") req->mdc)
        request (mock-request)
        _response (handler request)]
    (is (nil? (MDC/get "x-session-id")))))

(deftest test-wrap-mdc-stringifies-keys
  (let [handler (wrap-mdc (build-mdc-echo-handler "keyword") (constantly {:keyword "value"}))
        request (mock-request)
        response (handler request)]
    (is (= "value" (response :body)))))

(deftest test-wrap-mdc-supports-stringifies-values
  (let [handler (wrap-mdc (build-mdc-echo-handler "keyword") (constantly {:keyword 123}))
        request (mock-request)
        response (handler request)]
    (is (= "123" (response :body)))))

(deftest test-wrap-mdc-does-not-clear-other-data
  (let [handler (wrap-mdc (build-mdc-echo-handler "x-session-id") req->mdc)
        request (mock-request)
        _response (handler request)]
    (is (= "value" (MDC/get "other")))))

(deftest test-clear-mdc
  (let [handler (wrap-clear-mdc (build-mdc-echo-handler "x-session-id"))
        request (mock-request)
        _response (handler request)]
    (is (nil? (MDC/get "other")))))
