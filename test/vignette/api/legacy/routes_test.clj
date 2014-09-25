(ns vignette.api.legacy.routes-test
  (:require [vignette.api.legacy.routes :as routes]
            [clout.core :refer (route-compile route-matches)]
            [ring.mock.request :refer :all]
            [midje.sweet :refer :all]))

(facts :thumbnail-route
  (let [matched (route-matches routes/thumbnail-route
                               (request :get "/happywheels/images/thumb/b/bb/SuperMario64_20.png/185px-SuperMario64_20.webp"))
        matched (routes/route->thumb-map matched)]
    (:request-type matched) => :thumbnail
    (:archive matched) => ""
    (:original matched) => "SuperMario64_20.png"
    (:middle-dir matched) => "bb"
    (:top-dir matched) => "b"
    (:image-type matched) => "images"
    (:width matched) => "185"
    (:wikia matched) => "happywheels"
    (:revision matched) => "latest"
    (:thumbname matched) => "185px-SuperMario64_20.webp"
    (:format (:options matched)) => "webp")

  (let [matched (routes/route->thumb-map
                  (route-matches routes/thumbnail-route
                                 (request :get "/charmed/images/thumb/archive/b/b6/20101213101955!6x01-Phoebe.jpg/479px-6x01-Phoebe.jpg")))]
    (:request-type matched) => :thumbnail
    (:wikia matched) => "charmed"
    (:image-type matched) => "images"
    (:archive matched) => "/archive"
    (:top-dir matched) => "b"
    (:middle-dir matched) => "b6"
    (:original matched) => "20101213101955!6x01-Phoebe.jpg"
    (:width matched) => "479"
    (:revision matched) => "20101213101955"
    (:thumbname matched) => "479px-6x01-Phoebe.jpg"
    (:format (:options matched)) => "jpg"))

;(facts :original-route
;       [map (routes/route->original-map
;              (route-matches routes/original-route
;                             (request :get "/happywheels/images/b/bb/SuperMario64_20.png")))])

;(facts :image-thumbnail-width
;  (let [rt (routes/add-request-type {} :image-thumbnail)]
;    (routes/request-map-add-width
;       (merge rt {:thumbnail "130px-102,382,0,247-Brain_Attack.PNG"} ))  => (contains {:width "130px"})
;    (routes/request-map-add-width
;       (merge rt {:thumbnail "10x10-102,382,0,247-Brain_Attack.PNG"} ))  => (contains {:width "10x10"})
;    (routes/request-map-add-width
;       (merge rt {:thumbnail "10x10x10-102,382,0,247-Brain_Attack.PNG"} ))  => (contains {:width "10x10x10"})
;    (routes/request-map-add-width
;       {:thumbnail "10x10x10-102,382,0,247-Brain_Attack.PNG"})  => (contains {:width ""})))
;
;(facts :coordinates
;  (routes/thumbnail-path->coordinates "") => falsey
;  (routes/thumbnail-path->coordinates "0,0,0") => falsey
;  (routes/thumbnail-path->coordinates "0,0,0,0") => (contains {:x1 0 :x2 0 :y1 0 :y2 0})
;  (routes/thumbnail-path->coordinates "1,2,3,4") => (contains {:x1 1 :x2 2 :y1 3 :y2 4}))
;
;(facts :dimensions
;  (routes/thumbnail-path->dimensions "") => falsey
;  (routes/thumbnail-path->dimensions "foobar.png") => falsey
;  (routes/thumbnail-path->dimensions "123px-foobar.png") => truthy
;  (routes/thumbnail-path->dimensions "123px-foobar.png") => [:width '(123)]
;  (routes/thumbnail-path->dimensions "123x123-foobar.png") => [:width-height '(123 123)])

; this is specific to the no-extension request type
; (routes/thumbnail-path->dimensions "123x124x10-foobar") => [:width-height-scale '(123 124 10)]
