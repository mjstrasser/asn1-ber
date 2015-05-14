package asn1.ber

import org.scalatest.FlatSpec

class BerBooleanTest extends FlatSpec {

  val TRUE = true
  val FALSE = false

  "BerBoolean" should "decode a boolean value correctly" in {

    val falseBytes: Seq[Byte] = Seq(1, 1, 0)
    assert(Ber.decode(falseBytes) == (BerBoolean(FALSE), Seq()))

    val trueBytes: Seq[Byte] = Seq(1, 1, 0xFF.toByte)
    assert(Ber.decode(trueBytes) == (BerBoolean(TRUE), Seq()))
  }

}
