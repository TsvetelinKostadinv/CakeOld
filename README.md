# Cake
Make programming a piece of cake. \
 \
This is a custom programming language written in Java for learning purposes.

## Main goals
The main purpose of this project is to develop a language fully object-oriented in design. \
and yet so easy and simple to understand to be taught at schools. 

## Grammar(Syntax)

### Modifiers
These modifiers aim to either reduce/enlarge the scope of the modified token or change it's functionality. \
These modifiers can be **global**, **public**, **bundle**, **groupscoped**, **private** for enscoping. \
For functionality **final** can be used for making a variable immutable. For classes **final** denotes that it cannot be inherited 

#### Something to be said about modifiers
The **final** modifier can be used for variables to make them constants, however, you can declare a variable as **final** but don't give it value, in which case you can set it's value in the constructor

### Literals
A literal is called any statically typed expression.
* One example is the string literal("Hello", "1", "true"). String literal is defined as: Any sequence of characters enclosed in quotes 
* Another example is the number literal(-32, 0, 40, 3.1415). Integers are not discriminated against real numbers. If, however, you desire to differentiate(or it is just more suitable for your application) you can use **integer** and **real** as types. 
* And the last example I am going to give are the boolean literals **true** and **false**

### Identifiers
Simply said the name. More technically put it's the sequence of characters starting with a letter(lower or upper case). \
After that may come letters and numbers.


### Types
The types are what you can expect of any other language. 
* **number** - used for numbers but does not discriminate between real and integers. It is bound tightly bound, however if more concrete types are used the bounds are pushed to a bigger range. 
* **boolean** - used for logical operations. Has 2 values **true** and **false** 
* **string** - defined by the string literal 
* And perhaps one unusual would be the **method** - this is the type used to refer to the methods 
* Lastly every class defines a type which can be used for a variable                                                                         

### Blocks
A block is defined by: the list of commands wrapped in "{}". The block may encompass methods, local types, variables. \
Everything declared in a block is automatically scoped in the block and **cannot** be taken out except by a return of a method

### Project organization
Every project has to have an architecture. So in this case it can be built into the language by introducing bundles. \
The bundles are a representation of the file/folder system, the Java package system, etc. 
* PRP - PRP stands for "Project Relative Path", this path is formed by: 
      
1. Taking the project name 
2. Adding a "."(dot) 
3. Adding a folder name 
4. Repeating steps 2,3 until you reach the directory of the file 

Example may be: ExampleProject.src.SubBundle \
This annotates that the source with this PRP is located in the folder named "SubBundle" 
```
bundle <PRP>
```

### Single- and Multi-line comments
A feature of every self respecting language are comments. \
As not to introduce many new characters I keep the stereotypes. \
In this case:

```
//for single line comments

/*
 * For multiline comments
 */
```

### Attribute definition:
Perhaps one of the more interesting parts of the language is that you can define the keyword set along with other useful attributes \
such as encoding, auto-loading of the class(or it will be loaded on demand) etc. \
Here should be noted that this is defined before everything else( at the top of the file if you will ) 
* attribute_type - a specific predefined type from a set statically typed in the language( for now ) 
* value - a value which has to again match a predefined statically typed list of values for the given attribute_type
  
```
|!| <attribute_type> : <value>
```
### Class definition:
As an object-oriented language it is needed to have the functionality of classes as templates for, well, objects. 
* access_modifier - See "[Modifiers]" section 
* class_modifiers - See [Modifiers] section 
* identifier - See "[Identifiers]" section 
```
[access_modifier] [class_modifiers] class <identifier> [ extends <identifier_parentClass> ] [ implements <identifier_interface> ]
```

### Variable declaration
Variables are a crucial part of any program.  \
There are 3 parts of a variable: name, type and value. \
The name is a simple identifier([identifiers]) \
Every variable when initialized, but not given a value will be initialized with the default value of the type it is declared as. \
Many languages have dynamic variables(you can change their type in runtime).However, I don't like that idea because it makes it difficult to keep track of everything. So they are permanently typed. 
* access_modifier - See "[Modifiers]" section 
* identifier - See "[Identifiers]" section 
* literal - See "[Literals]" section 
* type - See "[Types]" section 
```
[access_modifier] <identifier> <type> [ = <identfier OR literal>]
```

### Arrays declaration
Arrays as a list of values of the same type are a little outdated. So I am going to adopt the technology of modern languages \
and let them be of different types. As for the code, they are defined more or less like a simple variable. 
* access_modifier - See "[Modifiers]" section 
* identifier - See "[Identifiers]" section 
* CSV - or comma separated values means that you must supply values separated by commas
```
[access_modifier] <identifier> [ = { <CSV> } ]
```

### If statement
The trusty if. It would be a shame if we left him out. He is a proven in time helper. Does not need much explanation. Executes the block of code if the condition is **true**
* condition - an expression which evaluates to **true** or **false**
* block - see "[Blocks]" section 
```
if <condition> <block>
```

### Switch statement
One of the dinosaurs of programming languages is the switch. It can be pretty useful in some cases which pushes me into the implementation of this selection control mechanism.
* identifier - See "[Identifiers]" section
* cases - the values which will be checked against. If they are equal the block after the case is executed
* else block is executed if no other block was executed
```
switch <identifier>
<case1_value> , <case2_value> , <case3_value> ... -> <block>
...

else -> <block>
```

### Loops
One cannot simply omit loops. They are really handy tools in every language. So they are here as well. \
While: 
* condition - the same type of condition as with the "if"
* block - see "[Blocks]" section 

For : 
* variable_declaration - variables used in the loop(consult the [variable declaration section](https://github.com/TsvetelinKostadinv/Cake#variable-declaration)) 
* condition - the same type of condition as with the "if"
* step - the last command which will be executed in every iteration

For each variant:
* type - the type of the elements over which the loop will be going(consult the [types] section)
```
while <condition> <block>

for <variable_declaration> , <condition> , <step> <block>

for <type> <identifier> in <iterable_structure> <block>
```

### Method declaration
The intricate nature of OOP slightly suggests the idea that everything should be modeled after real life. Well I took the idea and \
raised it to the next level. Methods will be treated as variables in most cases while keeping the core functionality of methods. 
* access_modifier - See "[Modifiers]" section 
* identifier - See "[Identifiers]" section 
* typeS - See "[Types]" section (A method can return multiple values of different types) 
* block - See "[Blocks]" section 
```
[access_modifier] <identifier> <typeS> = ( [parameter_identifiers] ) [ -> <block> ]
```

### Casting variables
Perhaps not widely used in most languages but i want to encourage it. \
This is because every class can declare special methods in a special method group in order to be cast properly 
```
int i = 0
string zero = i as string
```

### Method groups
More of a aesthetic point of view you can organize your methods and variables into groups and if you wish you can define a method or variable as "groupscoped" and it won't be visible in other groups.
* identifier - the same applies here with the identifier as with any other 
* block - See "[Blocks]" section 
```
group <identifier> <block>
```

### Special method groups
Such special method group may be the casting method group, which defines how a variable will be cast to other classes \
Another example will be the comparison group, in which you will define logical behavior of your classes and objects \
It is a useful technology when for example when you want to compare two objects of the same type but they are not numbers \
For example you have defined a Coordinate object. You cannot simply say coord1 > coord2 and expect it to return true or false. \
Well now you can. By overriding special non-obligatory method group(**comparison** group) in your classes you can define your own behavior. \
Another non-obligatory group is the **casting** group which defines the casting behavior of objects 


[Modifiers]:https://github.com/TsvetelinKostadinv/Cake#modifiers
[Literals]:https://github.com/TsvetelinKostadinv/Cake#literals
[Identifiers]:https://github.com/TsvetelinKostadinv/Cake#identifiers
[Types]:https://github.com/TsvetelinKostadinv/Cake#types
[Blocks]:https://github.com/TsvetelinKostadinv/Cake#blocks