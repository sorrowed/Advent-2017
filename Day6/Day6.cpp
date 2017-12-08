#include<vector>
#include<string>
#include<sstream>
#include<iostream>
#include<algorithm>

std::vector< int > banks( { 0, 5, 10, 0, 11, 14, 13, 4, 11, 8, 8, 7, 1, 4, 12, 11 } );
typedef std::vector< std::string > history_t;
history_t history;

std::string get_state( const std::vector< int >& src )
{

	std::stringstream str;
	for( auto v : src ) {
		str << v << ':';
	}
	return str.str();
}

std::pair< history_t::size_type, std::string > part1()
{
	std::string state = get_state( banks );

	while( std::find( history.begin(), history.end(), state ) == history.end() ) {

		history.push_back( state );

		auto mx = std::max_element( banks.begin(), banks.end() );

		int divide = *mx;
		*mx = 0;

		for( auto ix = mx + 1; divide > 0; ++ix ) {
			if( ix == banks.end() ) {
				ix = banks.begin();
			}
			*ix += 1;
			--divide;
		}

		state = get_state( banks );
	}
	return std::make_pair( history.size(), state );
}

std::vector< std::string >::size_type part2( const std::string& state )
{

	auto f = std::find( history.begin(), history.end(), state );

	return history.end() - f;
}

int main( int argc, char* argv[] )
{

	auto r = part1();

	std::cout << "Part 1  : Finished after " << r.first << " steps\n";
	std::cout << "Part 2 :  Loop is " << part2( r.second ) << " steps long\n";

	return 0;
}
