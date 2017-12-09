#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct Node_t_struct Node_t;

struct Node_t_struct
{
	char Name[ 32 ];
	int Weight;

	int TotalWeight;

	int ChildCount;
	Node_t **Children;
};

void node_print( const Node_t* node )
{
	printf( "Name: %s Weight: %d Total: %d Children: %d [ ", node->Name, node->Weight, node->TotalWeight, node->ChildCount );
	for( int i = 0; i < node->ChildCount; ++i ) {
		printf( "%s ", node->Children[ i ]->Name );
	}
	printf( "]" );
}

Node_t* node_find( Node_t* nodes[], const char * name )
{
	Node_t* r = NULL;
	while( *nodes && r == NULL ) {

		if( !strcmp( (*nodes)->Name, name ) ) {
			r = *nodes;
		} else {
			++nodes;
		}
	}

	return r;
}


void node_set_total_weight( Node_t* node )
{
	node->TotalWeight = node->Weight;
	for( int i = 0; i < node->ChildCount; ++i )
		node->TotalWeight += node->Children[ i ]->TotalWeight;
}

int node_add_child( Node_t* parent, Node_t* child )
{
	size_t sz = parent->ChildCount * sizeof( Node_t* );

	Node_t** children = realloc( parent->Children, sz + sizeof( Node_t* ) );
	if( children == NULL )
		return ENOMEM;
	children[ parent->ChildCount++ ] = child;
	parent->Children = children;

	return 0;
}

Node_t* node_create( Node_t** nodes, Node_t* node, const char* src )
{
	sscanf( src, "%s %*c%d", node->Name, &node->Weight );

	const char * children = strstr( src, " -> " );
	if( children != NULL ) {
		children += 4;

		const char* const endptr = src + strlen( src );

		char name[ 32 ] = { 0 };

		while( node != NULL && children < endptr && sscanf( children, "%s", name ) > 0 ) {

			int len = strlen( name );
			children += (len + 1);
			if( name[ len - 1 ] == ',' )
				name[ len - 1 ] = 0;

			Node_t* child = node_find( nodes, name );
			if( child != NULL ) {
				node_add_child( node, child );
				memset( name, 0 , sizeof( name ) );
				node_set_total_weight( node );
			} else {
				// Damn, that didn't work. Gotta do it all over again
				free( node->Children );
				node->Children = 0;
				node->ChildCount = 0;

				node = NULL;
			}
		}
	}

	return node;
}

char** file_read_all_Lines( int* count )
{
	FILE* file = fopen( "input.txt", "r" );

	*count = 0;

	char** lines = malloc( 1300 * sizeof(char*) );
	char* line = malloc( 512 );
	while( fgets( line, 512, file ) ) {
		lines[ (*count)++ ] = line;
		line = malloc( 512 );
	}
	free( line );

	fclose( file );

	return realloc( lines, *count * sizeof(char*) );
}

Node_t** node_read_input( char** lines, int count )
{
	Node_t** nodes = calloc( count + 1, sizeof( Node_t* ) );

	int created = 0;
	while( created < count ) {

		Node_t* current = nodes[ created ] = calloc( 1, sizeof( Node_t ) );

		for( int i = 0; i < count; ++i ) {

			if( lines[i] != NULL ) {

				if( node_create( nodes, current, lines[ i ] ) != NULL ) {

					++created;

					free( lines [ i ] );
					lines[i] = NULL;

					break;
				}
			}
		}

	};


	return nodes;
}

Node_t* node_find_root( Node_t** nodes, int count )
{
	Node_t* r = NULL;

	for( int i = 0; r == NULL && i < count; ++i ){
		Node_t* first = nodes[ i ];

		for( int j = 0; j < count; ++j ) {

			Node_t* second = nodes[ j ];

			for( int k = 0; k < second->ChildCount; ++k ) {
				if( second->Children[ k ] == first ) {
					goto nope;
				}
			}
		}
yup:
		r = first;
nope:
		;
	}

	return r;
}

Node_t* part1( void )
{
	int count = 0;
	char** lines = file_read_all_Lines( &count );
	Node_t** nodes = node_read_input( lines, count );
	free( lines );

	return node_find_root( nodes, count );
}

Node_t* part2( Node_t* node )
{
	Node_t* r = NULL;

	int max = INT_MIN;
	int min = INT_MAX;

	for( int k = 0; k < node->ChildCount; ++k ) {

		max = node->Children[ k ]->TotalWeight > max ? node->Children[ k ]->TotalWeight : max;
		min = node->Children[ k ]->TotalWeight < min ? node->Children[ k ]->TotalWeight : min;

	}

	if( min != max ) {

	}

	return r;
}


int main( int argc, char* argv[] ){

	Node_t* node = part1();

	if( node != NULL ) {
		printf( "Part1 --> " );
		node_print( node );
		printf( "\n" );
	}

	Node_t* err = part2( node );
	if( node != NULL ) {
		printf( "Part2 --> " );
		node_print( node );
		printf( "\n" );
	}

	return 0;
}
