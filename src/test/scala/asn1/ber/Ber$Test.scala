package asn1.ber

import org.scalatest.FlatSpec

class Ber$Test extends FlatSpec {

  "Ber.decode" should "decode a boolean value correctly" in {

    val falseBytes: Seq[Byte] = Seq(1, 1, 0)
    assert(Ber.decode(falseBytes) == (BerBoolean(false), Seq()))

    val trueBytes: Seq[Byte] = Seq(1, 1, 0xFF.toByte)
    assert(Ber.decode(trueBytes) == (BerBoolean(true), Seq()))
  }

}
