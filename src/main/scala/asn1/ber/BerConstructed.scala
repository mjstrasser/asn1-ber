package asn1.ber

class BerConstructed(classAndPc: ClassAndPC, tag: Int, val value: Seq[DataValue]) extends DataValue(classAndPc, tag) {
  override def toBytes = ???
  override def equals(other: Any) = other match {
    case that: BerConstructed => this.classAndPc == that.classAndPc && this.tag == that.tag && this.value == that.value
    case _ => false
  }
  override def hashCode = 41 * (41 * (classAndPc.hashCode + 41) + tag.hashCode) + value.hashCode
  def canEqual(other: Any) = other.isInstanceOf[BerConstructed]
  override def toString = s"BerConstructed($value)"
}

object BerConstructed {

  def apply(classAndPC: ClassAndPC, tag: Int, value: Seq[DataValue]): DataValue =
    new BerConstructed(classAndPC, tag, value)

  def decode(classAndPC: ClassAndPC, tag: Int, value: Seq[Byte]): DataValue = {
    def appendBer(octets: Seq[Byte], berSeq: Seq[DataValue]): Seq[DataValue] = {
      if (octets.isEmpty)
        berSeq
      else {
        val (ber, tail) = Ber.decode(octets)
        appendBer(tail, berSeq :+ ber)
      }
    }
    new BerConstructed(classAndPC, tag, appendBer(value, Seq()))
  }

}
