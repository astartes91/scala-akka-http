package akkahttp

class Storage[K, V] {
  protected val data = collection.mutable.HashMap.empty[K, V]

  def list: Seq[V] = data.values.toSeq
  def put(key: K, value: V) {
    data.put(key, value)
  }
}

class AuthorsStorage() extends Storage[AuthorCode, Author]

class BooksStorage() extends Storage[BookCode, Book]
