package ir.arash.altafi.musiccompose.utils.ext

fun Any.getHashLong(): Long {
    return this.hashCode().toLong()
}

fun Any.getHashInt(): Int {
    return this.hashCode()
}
