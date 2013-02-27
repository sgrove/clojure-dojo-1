(ns clojure-dojo.core
  (:require [clojure.string :as string])
  (:use net.cgrand.enlive-html)
  (:import java.net.URL))

(defn get-front-page []
  (let [uri "http://news.ycombinator.com"
        url (URL. uri)
        parsed-html (html-resource url)
        title-tags (select parsed-html [:.title :a])
        titles (map (comp first :content) title-tags)
        point-tags (select parsed-html [:.subtext :span])
        points (map (comp read-string #(first (string/split % #" ")) first :content) point-tags)
        time (java.lang.System/currentTimeMillis)
        points-time-map (map (fn [p] {:time time :points p}) points)
        ]
    (zipmap titles points-time-map)))