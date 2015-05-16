package asn1.ber

import org.scalatest.FlatSpec

class BerOctetStringTest extends FlatSpec {

  "BerOctetString" should "decode string values correctly" in {
    assert(Ber.decode(Seq(4, 5, 72, 101, 108, 108, 111)) == (BerOctetString("Hello"), Seq()))
  }
  it should "encode string values correctly" in {
    assert(BerOctetString("Hello").toBytes == Seq(4, 5, 72, 101, 108, 108, 111))
  }

}
