(ns ring.sse-test
  (:require [clojure.test :refer [deftest is]]
            [ring.sse :as sse]
            [ring.mock.request :as mock]))

(deftest sse-start-stream
  (let [request       (-> (mock/request :get "/events")
                          #_(mock/header  "origin" "http://foo.com:8080"))
        handler       (sse/event-channel-handler (fn [request response raise ch] ch))
        response      (atom nil)
        respond       #(reset! response %)
        _             (handler request respond identity)
        {body :body
         {content-type "Content-Type"
          connection "Connection"
          cache-control "Cache-Control"
          allow-origin "Access-Control-Allow-Origin"} :headers
         status :status} @response]
    (is body "Response has a body")
    (is (instance? clojure.core.async.impl.protocols.Channel body) "Response body is a channel")
    (is (= 200 status) "A successful status code is sent to the client.")
    (is (= "text/event-stream; charset=UTF-8" content-type)
        "The mime type and character encoding are set with the servlet setContentType method")
    (is (= "close" connection) "The client is instructed to close the connection.")
    (is (= "no-cache" cache-control) "The client is instructed not to cache the event stream")
    #_(is (= "http://foo.com:8080" allow-origin)
          "The origin is allowed")))
