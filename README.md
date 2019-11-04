# ring-mdc

[![Clojars Project](https://img.shields.io/clojars/v/tessellator/ring-mdc.svg)](https://clojars.org/tessellator/ring-mdc)

Ring middlewares that interact with the Mapped Diagnostic Context (MDC).

NOTE: Because the internals depend on thread-local bindings, these middlewares
cannot be used with async handlers.

## Usage

This library offers two ring middlewares: `wrap-mdc` and `wrap-clear-mdc`.

`wrap-mdc` is a middleware that takes a function that accepts a ring request and
returns a map of key/value pairs to be added to the MDC before the handler
executes. It also clears the keys it creates after the handler executes. The
following example demonstrates adding the HTTP headers to the MDC.

```clojure
(require '[ring.middleware.mdc :refer [wrap-mdc]])

(def handler
  (-> my-routes
      (wrap-mdc #(:headers %))))
```

`wrap-clear-mdc` is a middleware that clears the MDC after the handler exeuctes.
This is useful in ensuring that each request starts with a cleared MDC.

## License

Copyright © 2019 Thomas C. Taylor

Distributed under the Eclipse Public License version 2.0.
