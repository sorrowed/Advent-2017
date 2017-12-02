let fs = require('fs');

fs.readFile('input.txt', 'utf8', function(err,data) {
	
	let sum = 0;
	for( let i = 0; i < data.length; ++i ) {
		if( data[ i ] == data[ ( i + 1 ) % data.length ] ) {
			sum += parseInt( data[ i ] );
		}
	}
	console.log( sum );
	
	sum = 0;
	for( let i = 0; i < data.length; ++i ) {
		if( data[ i ] == data[ ( i + data.length / 2 ) % data.length ] ) {
			sum += parseInt( data[ i ] );
		}
	}
	console.log( sum );
});
