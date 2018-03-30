(ns trolley-dilemma.views)

(defn message-item [_ text]
    [:p text])

(defn messages-container [messages]
    [:div.messages {:key "message-container"}
     (for [message messages
           :let [id (:id message)
                 text (:message message)]]
         [message-item {:key (str "message-" id)} text])])

(defn answer []
    [:p])

(defn home-page [messages]
    [:div {:key "home-page"}
     [:h2 "Trolley Dilemma: The Game"]
     [messages-container @messages]
     [answer]])