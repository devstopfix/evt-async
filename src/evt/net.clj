(ns evt.net
  (:require [clj-http.client :as h])
  (:use 
    [cheshire.core :only [generate-string]]))

;#? (:clj

(defn auth-header [req key]
  "Header to send API key"
  (assoc-in req [:headers "Authorization" ] key ))

(defn content-type-json [req]
  (assoc req :content-type :json ))

(defn json-header [req]
  "Headers to request JSON"
  (merge req {:accept :json
              :as :json}))

(defn evt-headers 
  ([key] (evt-headers key {}))
  ([key defaults]
    (->
      (json-header defaults)
      (auth-header key))))

(defn json-body [req body]
  (assoc req :body (h/json-encode body)))

(defn evt-headers [key]
  (->
    (json-header {})
    (auth-header key)))

(defn get-json [key url]
  "GET the given url as JSON, returns a map."
    (h/get url (evt-headers key)))

(defn put-json [key url body]
  "Put the body as JSON to the given URL"
  (h/put url
    {:body (h/json-encode body)
    :headers {"Authorization" key}
    :content-type :json
    :accept :json
    :as :json}))

(defn delete [key url]
  "Send HTTP DELETE to given URL with given key"
  (h/delete url (evt-headers key {:throw-exceptions false})))

(defn body [res]
  "The body of an HTTP response as a map"
  (:body res))
