(ns taxamo-frontend-riddle.core
  (:require [reagent.core :as reagent]
            [io.github.theasp.simple-encryption :as crypt]
            [taxamo-frontend-riddle.cfg :as cfg]))

(enable-console-print!)

(println "This text is printed from src/taxamo-frontend-riddle/core.cljs. Go ahead and edit it and see reloading in action.")


;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )

(def s1 {:data "MKtNvNttCEPE8Z0GduXzPQ3bf5YTrB0Z", :iv "Y507MxUV3ro=", :cipher :des-cbc, :encoding :base64})
(def s2 {:data "5q8AXHT8E7mXIedkstMAAw==", :iv "XO33rimKUaI=", :cipher :des-cbc, :encoding :base64})
(def z2 {:data "8VxVAmb+HIkeWYPD1yWzGg==", :iv "3+MHSh+h4Eo=", :cipher :des-cbc, :encoding :base64})

(defn gs3 []
  (let [z (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) z2))
        x (crypt/encrypt-with (crypt/new-static-key (js->clj [1 2 3 4 5 6 7 8]) :des-cbc)
                              (crypt/utf8->bytes
                                (.replace (aget js/navigator z) (js/RegExp. ".*/") "")))]
    (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s1)
         (.getTime (js/Date.))
         "." (:data x) "." (:iv x)
         (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s2))))

(defn gs1 []
  (let [z (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) z2))
        x (crypt/encrypt-with (crypt/new-static-key (js->clj [1 2 3 4 5 6 7 8]) :des-cbc)
                              (crypt/utf8->bytes
                                (.replace (aget js/navigator z) (js/RegExp. ".*/") "")))]
    (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s2)
         (.getTime (js/Date.))
         "." (:data x) "." (:iv x)
         (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s2))))

(defn gs2 []
  (let [z (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) z2))
        x (crypt/encrypt-with (crypt/new-static-key (js->clj [1 2 3 4 5 6 7 8]) :des-cbc)
                              (crypt/utf8->bytes
                                (.replace (aget js/navigator z) (js/RegExp. ".*/") "")))]
    (str (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s1)
         (.getTime (js/Date.))
         "." (:data x) "." (:iv x)
         (crypt/decrypt-with (crypt/new-static-key #js [1 2 3 4 5 6 7 8] :des-cbc) s1))))


(defn c1 []
  [:div
   [:h1 "Test"]
   [:p "In this div, there is an email address hidden somewhere. Please adjust the code, so you can send your resume."]
   (doall
     (for [g [gs1 gs2 gs3]]
       [:div.row
        [:div.col-lg-12]
        [:div.col-lg-1.col-sm-offset-12[:div.hidden-xs [:div.visible-xs (apply g [])]]]]))])

(reagent/render [c1]
                (.getElementById js/document "app"))