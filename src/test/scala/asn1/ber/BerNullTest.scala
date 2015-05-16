package asn1.ber

import org.scalatest.FlatSpec

class BerNullTest extends FlatSpec {

  "BerNull" should "be decoded correctly" in {
    assert(Ber.decode(Seq(5, 0)) == (BerNull, Seq()))
  }
  it should "be encoded correctly" in {
    assert(BerNull.toBytes == Seq(5, 0))
  }

  "BerEndOfContent" should "be decoded correctly" in {
    assert(Ber.decode(Seq(0, 0)) == (BerEndOfContent, Seq()))
  }
  it should "be encoded correctly" in {
    assert(BerEndOfContent.toBytes == Seq(0, 0))
  }

}
