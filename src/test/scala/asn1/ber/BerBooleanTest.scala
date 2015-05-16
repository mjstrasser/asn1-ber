package asn1.ber

import org.scalatest.FlatSpec

class BerBooleanTest extends FlatSpec {

  val falseBytes: Seq[Byte] = Seq(1, 1, 0)
  val trueBytes: Seq[Byte] = Seq(1, 1, 0xFF.toByte)

  "BerBoolean" should "decode a boolean value correctly" in {
    assert(Ber.decode(falseBytes) == (BerBoolean(value = false), Seq()))
    assert(Ber.decode(trueBytes) == (BerBoolean(value = true), Seq()))
  }

  it should "encode a boolean value correctly" in {
    assert(BerBoolean(value = false).toBytes == falseBytes)
    assert(BerBoolean(value = true).toBytes == trueBytes)
  }

}
