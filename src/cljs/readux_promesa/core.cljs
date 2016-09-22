(ns readux-promesa.core
  (:require [promesa.core :as p]))

(defn- keyword-append
  "Construct a keyword by appending an arbitrary sequence of strings/keywords"
  [& args]
  (->> args
       (map name)
       (apply str)
       keyword))

(defn- promesa-action
  [action]
  (cond
    (p/promise? (:payload action))
    {:promise (:payload action) :data nil}

    (p/promise? (when (map? (:payload action))
                  (get-in action [:payload :promise])))
    (select-keys (:payload action) [:promise :data])))

(defn mw-promesa
  [dispatch next model action]
  (when-let [{:keys [promise data]} (promesa-action action)]
    (do (let [on-success (keyword-append action ".success")
              on-error (keyword-append action ".error")
              rq (keyword-append action ".rq")]
          (dispatch {:type rq :payload data})
          (-> promise
              (p/then #(dispatch
                        {:type on-success :payload {:rsp  %
                                                    :data data}}))
              (p/catch #(dispatch
                         {:type on-error :payload {:rsp  %
                                                   :data data} :error true}))))
        model)))