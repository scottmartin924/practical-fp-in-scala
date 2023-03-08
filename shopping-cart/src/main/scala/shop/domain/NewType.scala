package shop.domain

// Blatantly stolen from https://contributors.scala-lang.org/t/synthesize-constructor-for-opaque-types/4616/64
// a discussion about opaque types as a replacement for new types...we'll see if we like this or if
// just doing the opaque type for each individual thing with a custom apply/constructor is better
trait NewType[T] {
  opaque type Type = T

  inline def apply(w: T): Type = w

  extension (t: Type) {
    inline def unwrap: T = t
  }
}
