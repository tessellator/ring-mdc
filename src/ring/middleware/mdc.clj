(ns ring.middleware.mdc
  "Ring middlewares for interacting with SLF4J's Mapped Diagnostic Context."
  (:import org.slf4j.MDC))

(defn- put-all! [m]
  (doseq [[k v] m]
    (MDC/put k v)))

(defn- remove-all! [ks]
  (doseq [k ks]
    (MDC/remove k)))

(defn- clear! []
  (MDC/clear))

(defn wrap-mdc
  "A ring middleware that applies the k/v pairs from `(f request)` to the MDC
  and clears the keys from the MDC after the handler executes.

  Only the keys defined in `(f request)` are cleared; other keys are left
  intact.

  Note that this relies on thread-local bindings, so you cannot use async
  handlers if you use this middleware."
  [handler f]
  (fn [request]
    (let [m (f request)]
      (put-all! m)
      (let [response (handler request)]
        (remove-all! (keys m))
        response))))

(defn wrap-clear-mdc
  "A ring middleware that clears the MDC after the handler executes.

  Note that this relies on thread-local bindings, so you cannot use async
  handlers if you use this middleware."
  [handler]
  (fn [request]
    (let [response (handler request)]
      (clear!)
      response)))
