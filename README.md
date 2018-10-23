# LangDev

This is a custom programming language written in Java for learning purposes.

## Main goals
The main purpose of this project is to develop a language fully object-oriented in design.                                          <br>
and yet so easy and simple to understand to be taught at schools.                                                                   <br>
The plan is to be able to change the keywords set to suit the language of education.                                               <br>

## Grammar(Syntax)

#### Single- and Multiline comments
A feature of every self respecting language are comments.                                                                           <br>
As not to introduce many new characters I keep the stereotypes.                                                                     <br>
In this case:

```
//for single line comments

/*
 * For multiline comments
 */
```

#### Attribute definition:
Perhaps one of the more interesting parts of the language is that you can define the keyword set along with other useful attributes <br>
such as encoding, auto-loading of the class(or it will be loaded on demand) etc.                                                    <br>
Here should be noted that this is defined before everything else( at the top of the file if you will )                              <br>
      attribute_type - a specific predefined type from a set statically typed in the language( for now )                          <br>
      value - a value which has to again match a predefined statically typed list of values for the given <attribute_type>        <br>
  
```
|!| <attribute_type> : <value>
```

#### Modifiers
These modifiers aim to either reduce/enlarge the scope of the modified token or change it's functionallity.                         <br>
These modifiers can be **global**, **public**, **bundle**, **groupscoped**, **private** for enscoping.                              <br>
For functionality **final** can be used for making a variable immutable. For classes **final** denotates that it cannot be inherited<br>

#### Literals
A literal is called any statically typed expression.                                                                                <br>
One example is the string literal("Hello", "1", "true"). String literal is defined as: Any sequence of characters enclosed in quotes<br>
Another example is the number literal(-32, 0, 40, 3.1415). Integers are not discriminated against real numbers. If, however, you desire to differenciate(or it is just more suitable for your application) you can use **integer** and **real** as types.                   <br>
And the last example I am going to give are the boolean literals **true** and **false**

#### Identifiers
Simply said the name. More technically put it's the sequence of characters starting with a letter(lower or upper case).             <br>
After that may come letters and numbers

#### Types
The types are what you can expect of any other language.                                                                            <br>
**number** - used for numbers but does not discriminate between real and integers. It is bound tightly bound, however               <br>
if more concrete types are used the bounds are pushed to a bigger range.                                                            <br>
**boolean** - used for logical operations. Has 2 values **true** and **false**                                                      <br>
**string** - defined by the string literal                                                                                          <br>
**method** - this is the type used to refer to the methods                                                                          <br>
Every class defines a type which can be used for a variable                                                                         

#### Blocks
A block is defined by: the list of commands wrapped in "{}". The block may encompass methods, local types, variables. Everything    <br>
decalred in a block is automatically scoped in the block and **cannot** be taken out except by a return of a method

#### Project organisation
Every project has to have an archetecture. So in this case it can be built into the language by introducing bundles.                <br>
The bundles are a representation of the file/foulder system, the Java package system, etc.                                          <br>
      PRP - PRP stands for "Project Relative Path", this path is formed by:                                                         <br>
        1. Taking the project name                                                                                                  <br>
        2. Adding a "."(dot)                                                                                                        <br>
        3. Adding a foulder name                                                                                                    <br>
        5. Repeating steps 2,3 until you reach the directory of the file                                                            <br>
  Example may be: ExampleProject.src.SubBundle                                                                                      <br>
  This annotates that the file with this PRP is located in the foulder named "SubBundle"                                            <br>
```
bundle <PRP>
```

#### Class definition:
As an object-oriented language it is needed to have the functionality of classes as templates for, well, objects.                   <br>
    access_modifier - See "Modifiers" section                                                                                       <br>
    class_modifiers - See "Modifiers" section                                                                                       <br>
    identifier - See "Identidiers" section                                                                                          <br>
```
[access_modifier] [class_modifiers] class <identifier> [ extends <identifier_parentClass> ] [ implements <identifier_interface> ]
```

#### Variable declaration
Variables are a crucial part of any program. Many languages have dynamic variables(you can change their type in runtime).           <br>
However, I don't like that idea because it makes it difficult to keep track of everything. So they are permanently typed.           <br>
    access_modifier - See "Modifiers" section                                                                                       <br>
    identifier - See "Identidiers" section                                                                                          <br>
    type - See "Types" section                                                                                                      <br>
```
[access_modifier] <identifier> <type> [ = <identfier OR literal>]
```

#### Arrays decalration
Arrays as a list of values of the same type are a little outdated. So I am going to adopt the technology of modern languages        <br>
and let them be of different types. As for the code, they are defined more or less like a simple variable.                          <br>
    access_modifier - See "Modifiers" section                                                                                       <br>
    identifier - See "Identifier" section                                                                                           <br>
    CSV - or comma separated values means that you must supply values separated by commas
```
[access_modifier] <identifier> [ = { <CSV> } ]
```

#### Method declaration
The intricate nature of OOP slightly suggests the idea that everything should be modeled after real life. Well I took the idea and  <br> raised it to the next level. Methods will be trated as variables in most cases while keeping the core functionality of methods.     <br>
    access_modifier - See "Modifiers" section                                                                                       <br>
    identifier - See "Identidiers" section                                                                                          <br>
    typeS - See "Types" section (A method can return multiple values of different types)                                            <br>
    block - See "Blocks" section                                                                                                    <br>
```
[access_modifier] <identifier> <typeS> = ( [paramer_identifiers] ) [ -> <block> ]
```

#### Casting variables
Perhaps not widely used in most languages but i want to encourage it.                                                               <br>
This is because every class can declare special methods in a special method group in order to be cast properly                      <br>
```
int i = 0
string zero = i as string
```

#### Method groups
More of a estetic point of view you can organise your methods and variables into groups and if you wish you can define a method or variable as "groupscoped" and it won't be visible in other groups.
    identifier - the same applies here with the identifier as with any other 
    block - See "Blocks" section
```
group <identifier> <block>
```

#### Special method groups
Such special method group may be the casting method group, which defines how a variable will be cast to other classes               <br>
Another example will be the comparison group, in which you will define logical behaviour of your classes and objects                <br>
It is a useful technology when for example when you want to compare two objects of the same type but they are not numbers           <br>
For example you have defined a Coordinate object. You cannot simply say coord1 > coord2 and expect it to return true or false.      <br>
Well now you can. By overriding special non-oblicatory method group(**comparison** group) in your classes you can define your own behaviour.                                                                                                                          <br>
Another non-oblicatory group is the **casting** group which defines the casting behaviour of objects                                <br>
