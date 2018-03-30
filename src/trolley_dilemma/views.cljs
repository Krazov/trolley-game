(ns trolley-dilemma.views)

(defn message-item [_ text]
    [:p text])

(defn messages-container [messages]
    [:div.trolley-messages {:key "message-container"}
     (for [message messages
           :let [id (:id message)
                 text (:message message)]]
         [message-item {:key (str "message-" id)} text])])

(defn answer [listen? yes no]
    [:div.trolley-choice {:class (if @listen? "is-active" "")}
     [:button.trolley-choice-yes
      {:on-click (fn [] (yes))}
      "Yes"]
     [:button.trolley-choice-no
      {:on-click (fn [] (no))}
      "No"]])

(defn home-page [messages listen? yes no]
    [:div.trolley {:key "home-page"}
     [:h2.trolley-header "Trolley Dilemma: The Game"]
     [messages-container @messages]
     [answer listen? yes no]])