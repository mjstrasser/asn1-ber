package asn1.ber

class BerBytes(classAndPc: ClassAndPC, tag: Int, val value: Seq[Byte]) extends DataValue(classAndPc, tag) {
  override def equals(other: Any) = other match {
    case that: BerBytes => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerBytes]
  override def toString = s"BerBytes($classAndPc,$tag,$value)"
  override def contentBytes = value.length.toByte +: value
}

object BerBytes {
  def apply(classAndPc: ClassAndPC, tag: Int, value: Seq[Byte]) = new BerBytes(classAndPc, tag, value)
  def apply(tag: Int, value: Seq[Byte]) = new BerBytes(ClassAndPC(Ber.Universal), tag, value)
}
