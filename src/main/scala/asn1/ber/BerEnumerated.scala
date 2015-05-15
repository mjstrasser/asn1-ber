package asn1.ber

class BerEnumerated(classAndPc: ClassAndPC, value: BigInt) extends BerInteger(classAndPc, Ber.Enumerated, value) {
  def this(value: BigInt) = this(ClassAndPC(Ber.Universal), value)
  override def toBytes = ???
  override def equals(other: Any) = other match {
    case that: BerEnumerated => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  override def canEqual(other: Any) = other.isInstanceOf[BerEnumerated]
  override def toString = s"BerEnumerated($value)"
}

object BerEnumerated {

  def apply(value: Int) = new BerEnumerated(value)

  def decode(classAndPC: ClassAndPC, valueOctets: Seq[Byte]) = BerInteger.decode(classAndPC, Ber.Enumerated, valueOctets)
}
