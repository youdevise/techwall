(ns techwall.technologies
  (:require [techwall.db :as db]))

(defn all [] (db/select "SELECT id, name FROM technologies"))
(defn find-by-id [id] (db/select-one "SELECT id, name FROM technologies WHERE id = ?" id))
(defn find-by-name [name] (db/select-one "SELECT id, name FROM technologies WHERE upper_name = UPPER(?)" name))

(defn- insert [name] (db/do-insert "INSERT INTO technologies (name, techtype_id)
                                    SELECT ?, MIN(id) FROM techtypes
                                     WHERE NOT EXISTS (SELECT 1
                                                 FROM technologies
                                                WHERE upper_name = UPPER(?))
                                     GROUP BY 1" name name))

(defn find-or-make
  ([id name]
    (if-let [existing (find-by-id id)]
      existing
      (find-or-make name)))
  ([name]
    (if-let [id (insert name)]
      {:id id :name name}
      (find-by-name name))))

