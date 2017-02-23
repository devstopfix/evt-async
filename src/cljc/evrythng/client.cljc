(ns evrythng.client "EVRYTHNG Client: HTTPS to REST API"
  (:require #?(:clj [clj-http.client :as http])
            #?(:clj [clojure.core.async
                     :as a
                     :refer [chan go >! close! dropping-buffer]])
            #?(:cljs [cljs-http.client :as http])
            #?(:cljs [cljs.core.async :as a :refer [chan dropping-buffer] ])))

(def request-headers {"Accept"     "application/json"
                      "User-Agent" "EvrythingAsync/0.1"})

(defn req-headers [key]
  (assoc request-headers "Authorization" key))

(def request-params {:throw-exceptions false
                     :conn-timeout 4000
                     :request-time 8000
                     :debug false})

(defn req-params [headers]
  (assoc request-params :headers headers))

(defn sink []
  (a/chan (a/dropping-buffer 1)))

#?(:clj
  (defn get-resource
    "GET the URL and place the response body string in a channel. Returns the chan and closes it.
     If the HTTP status is not 200, errors maps are put in the optional err chan."
    ([auth url] (get-resource auth url (sink)))
    ([auth url err]
     (let [out (a/chan)]
       (go
         (let [params (-> auth (req-headers) (req-params))
               response (http/get url params)]
           (case (:status response)
             200 (a/>! out response)
             (a/>! err response))
           (a/close! out)))
       out))))

#?(:cljs (defn get-resource "" [auth url]
         (let [params {:headers (req-headers auth)
                       :with-credentials? false}]
           (http/get url params))))