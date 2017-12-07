import java.io.File
import java.io.BufferedReader

fun main(args: Array<String>) {

	part1()
	part2()
}

fun hasDup( tokens : List<String> ) : Boolean {
	var r = false;
	for( t in tokens ) {
		r = r || tokens.count( { it == t } ) > 1
	}
	return r
}

fun part1() {
	
	val passphrases = ArrayList<String>()
	 
	val rdr: BufferedReader = File("input.txt").bufferedReader()

	rdr.useLines {

		lines->lines.forEach {
		
			val tokens = it.split(" ")

			if( !hasDup( tokens ) ) {
				passphrases.add( it )
			} else {
				println( "Passphrase discarded: " + it )
			}	
			
		}
	}

	println( "Part 1 -> Passphrases read : " + passphrases.count() )
}

fun part2() {
	
	val passphrases = ArrayList<String>()
	 
	val rdr: BufferedReader = File("input.txt").bufferedReader()

	rdr.useLines {

		lines->lines.forEach {
		
			val tokens = it.split(" ")
			
			val list = ArrayList<String>()
			for( s in tokens ) {
				list.add( s.toCharArray().sortedArray().joinToString("") )
			}
		
			if( !hasDup( list ) ) {
				passphrases.add( it )
			} else {
				println( "Passphrase discarded: " + it )
			}	
			
		}
	}

	println( "Part 2 -> Passphrases read : " + passphrases.count() )
}

