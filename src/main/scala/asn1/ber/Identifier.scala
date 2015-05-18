package asn1.ber

/**
 * Encapsulation of the identifier of a data value, created from
 * from the identifier octets.
 *
 * @param classAndPC a byte that encodes the class and primitive/constructed bit
 *                   of the data value
 * @param tag tag of the data value
 *
 */
class Identifier(val classAndPC: Byte, val tag: Int) {
  if (tag < 0 || tag > 30)
    throw new IllegalArgumentException("Only tag values between 0 and 30 are supported")

  def isUniversal = (classAndPC & 0xE0) == Ber.Universal
  def isApplication = (classAndPC & Ber.Private) == Ber.Application
  def isContextSpecific = (classAndPC & Ber.Private) == Ber.ContextSpecific
  def isPrivate = (classAndPC & Ber.Private) == Ber.Private
  def isConstructed = (classAndPC & Ber.Constructed) == Ber.Constructed
  def isPrimitive = (classAndPC & Ber.Constructed) != Ber.Constructed

  def toBytes: Seq[Byte] = Seq((classAndPC + tag).toByte)

  override def equals(other: Any) = other match {
    case that: Identifier => this.classAndPC == that.classAndPC && this.tag == that.tag
    case _ => false
  }
  override def hashCode = 41 * (41 * (classAndPC.hashCode + 41) + tag.hashCode)
  def canEqual(other: Any) = other.isInstanceOf[Identifier]
  override def toString = s"Identifier($classAndPC,$tag)"
}

object Identifier {
  def apply(classAndPC: Byte, tag: Int) = new Identifier(classAndPC, tag)
}

