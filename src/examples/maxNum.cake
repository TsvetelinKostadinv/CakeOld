global int main = ( ) -> {
	: local int a = 3 
	: local int b = 4 
	: if ( root.main#a > root.main#b ) { 
		: return root.main#a
	} 
	: if ( root.main#b > root.main#a ) { 
		: return root.main#b 
	} 
	: return 0 
}