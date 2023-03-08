package example.conventions

import example.conventions.Types.Username

class TypesSpec extends munit.FunSuite {
  test("valid username allowed") {
    val fakeUsername = "me@test.com"
    val user = Username.makeUsername(fakeUsername)
    assert(user.isRight)
    user.map(name => assertEquals(name.toString, fakeUsername))
  }
  test("invalid username disallowed") {
    val invalidUsername = "abc"
    val user = Username.makeUsername(invalidUsername)
    assert(user.isLeft)
  }
}
