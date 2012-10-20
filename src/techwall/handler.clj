(ns techwall.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defroutes app-routes
  (GET "/" [] (resp/redirect "/index.html"))
  (GET "/walls.json" [] {:headers {"Content-Type" "application/json"} :body "[{\"id\": 789, \"name\": \"Foo\"}]"})
  (GET "/walls/:id/wall.json" [] {:headers {"Content-Type" "application/json"} :body (str "{\"id\": " "2" "}")})
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
