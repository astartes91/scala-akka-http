package homework5

class Storage[K, V] {
  protected val data = collection.mutable.HashMap.empty[K, V]

  def list: Seq[V] = data.values.toSeq
}

class AuthorsStorage() extends Storage[AuthorCode, Author]
