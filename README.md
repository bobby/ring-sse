[![Build Status](https://travis-ci.org/bobby/ring-sse.svg?branch=master)](https://travis-ci.org/bobby/ring-sse)

# ring.sse

This is a small library implementing
[Server-Sent Events](https://www.w3.org/TR/2009/WD-eventsource-20090423/)
as an asynchronous
[Ring](https://github.com/ring-clojure/ring/blob/033a3235212dc71151c8e8c05069cb6c2dd85bdc/SPEC)
(spec 1.4+) handler.  This library is based on the excellent
implementation in
[Pedestal](https://github.com/pedestal/pedestal/blob/master/service/src/io/pedestal/http/sse.clj).

## Installation

``` clojure
[ring-sse/ring-sse "0.2.8"]
```

## Usage

``` clojure
(require '[ring.sse :as sse])

(def handler
  (sse/event-channel-handler
   (fn [request response raise event-ch]
     (a/go
       (dotimes [i 20]
         (let [event {:id   (java.util.UUID/randomUUID)
                      :name "foo"
                      :data (json/generate-string {:foo "bar"})}]
           (a/>! event-ch event)
           (a/<! (a/timeout 1000))))
       (a/close! event-ch)))
   {:on-client-disconnect #(log logger :info :sse/on-client-disconnect %)}))

;; invoked by an async adapter
(handler request respond-fn raise-fn)
```

## License

Copyright 2017 Bobby Calderwood

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
