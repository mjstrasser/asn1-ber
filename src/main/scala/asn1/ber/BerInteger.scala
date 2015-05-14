package asn1.ber

class BerInteger(classAndPc: ClassAndPC, val value: BigInt) extends DataValue(classAndPc, Ber.Integer) {
  def this(value: BigInt) = this(ClassAndPC(Ber.Universal), value)
  override def toBytes = ???
  override def equals(other: Any) = other match {
    case that: BerInteger => this.value == that.value
    case _ => false
  }
  override def hashCode = (value.hashCode + 41) * 41
  def canEqual(other: Any) = other.isInstanceOf[BerInteger]
  override def toString = s"BerInteger($value)"
}

object BerInteger {

  def apply(value: BigInt) = new BerInteger(value)

  // This code is adapted from the Ruby version in the net-ldap library.
  def decode(classAndPc: ClassAndPC, valueOctets: Seq[Byte]): DataValue = {
    // First bit indicates if the integer is negative.
    val negative = (valueOctets.head & 0x80) != 0x00
    // Read bytes, accumulating an unsigned value.
    def readOctet(octets: Seq[Byte], acc: BigInt): BigInt = {
      if (octets.length == 0)
        acc
      else {
        val nextUnsignedByte = octets.head & 0xFF
        val nextByte = if (negative) 255 - nextUnsignedByte else nextUnsignedByte
        readOctet(octets.tail, (acc << 8) + nextByte)
      }
    }
    val unsigned = readOctet(valueOctets, 0)
    // Correct for sign and return the value.
    val value = if (negative) (unsigned + 1) * -1 else unsigned
    BerInteger(value)
  }

  def decode(valueOctets: Seq[Byte]): DataValue = decode(ClassAndPC(Ber.Universal), valueOctets)
}
