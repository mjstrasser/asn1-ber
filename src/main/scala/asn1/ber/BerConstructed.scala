package asn1.ber

class BerConstructed(identifier: Identifier, val value: Seq[DataValue]) extends DataValue(identifier) {

  override def equals(other: Any) = other match {
    case that: BerConstructed => this.identifier == that.identifier && this.value == that.value
    case _ => false
  }
  override def hashCode = 41 * (41 * identifier.hashCode) + value.hashCode
  def canEqual(other: Any) = other.isInstanceOf[BerConstructed]
  override def toString = s"BerConstructed($identifier, $value)"
  override def contentBytes = {
    def gatherContents(contents: Seq[DataValue], accLength: Int, accBytes: Seq[Byte]): (Int, Seq[Byte]) = {
      if (contents.isEmpty)
        (accLength, accBytes)
      else {
        val headBytes = contents.head.toBytes
        gatherContents(contents.tail, accLength + headBytes.length, accBytes ++ headBytes)
      }
    }
    val (length, contents) = gatherContents(value, 0, Seq())
    length.toByte +: contents
  }
}

object BerConstructed {

  def apply(identifier: Identifier, value: DataValue*): DataValue =
    new BerConstructed(identifier, value)

  def appendBer(octets: Seq[Byte], berSeq: Seq[DataValue]): Seq[DataValue] = {
    if (octets.isEmpty)
      berSeq
    else {
      val (ber, tail) = Ber.decode(octets)
      appendBer(tail, berSeq :+ ber)
    }
  }

  def decode(identifier: Identifier, value: Seq[Byte]): DataValue = {
    new BerConstructed(identifier, appendBer(value, Seq()))
  }

}
