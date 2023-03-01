static void main(String[] args) {
//    closureString()
//    lazyString()
//    diffHashcode()
//    slashyString()
//    testList()
//    testMap()
//    testOperator()
//    testGetAndSet()
//    testClosure()
//    testRegExp()
//    testSpread()
//    println testDefArgs("hello world")
//    testTrait()
    testMixin()
}


trait TraitA {
    def methodInTraitA() {
        println "methodTritA"
    }

    String name = "default name"

}

class MixinA {
    def mixinMethod() {
        println "mixinMethod"
    }
}

class TraitClass implements TraitA {

}
/**
 * trait和mixin区别
 * trait 符合instance of,mixin不符合*/
static def testTrait() {
    def traitClass = new TraitClass()
    println traitClass.name
    traitClass.methodInTraitA()

}

static def testMixin() {
    TraitClass.mixin(MixinA)
    def clazz = new TraitClass()
    clazz.mixinMethod()
}

static def testDefArgs(par1, Integer par2 = 1) { [name: par1, age: par2] }

/**
 * w 是一个StringWriter*/
static void closureString() {
//    def closure = "hello ${w -> w << 'world'}"
//可以用语句,最后一个值作为返回值
    def closure = "${def a = 1; def b = 2; a + b}"
    println closure
}

/**
 *  ${-> number}是实时计算的*/
static void lazyString() {
    def number = 1
    def eagerGString = "value == ${number}"
    def lazyGString = "value == ${-> number}"

    assert eagerGString == "value == 1"
    assert lazyGString == "value == 1"

    number = 2
    assert eagerGString == "value == 1"
    assert lazyGString == "value == 2"
}

/**
 * String 和 GString的区别*/
static void diffHashcode() {
    def a = "hello"
    def b = "${'he'}" + "llo"
    println a == b  //true
    println a.hashCode() //hashcode 不一样
    println b.hashCode()
    println a.equals(b) //false
    println b.equals(a) //false
}

/**
 * 用在正则表达式或模板
 * 在正则表达式中就不用转义\了
 * /ends with slash ${'\'}/.
 * */
static void slashyString() {
    def name = "yuliyang"
    def a = /hello world $name/
    println a
}

static void testList() {
    def list = [1, "hello", 'hi', true]
    list.collect {}
}

static void testMap() {
    def map = [:]
//    map['name'] = 'yuliyang'
//    println map['name']

    String a = "name"
    map[(a)] = "yuliyang"
    println map['name']
}

static void testOperator() {
    def a = null
    //为null的时候才赋值
    a ?= 'hello'
    println a
}

class Person {
    String name
    int age

    void setName(String name) {
        println "invoke setName"
        this.name = name
    }

    void test() {
        println "test method"
    }
}

static void testGetAndSet() {
    def person = new Person()
    //这样会调用set方法
//    person.name = "hello"
    //直接属性访问
//    person.@name = "world"
    def testFun = person.&test
    testFun()
    testFun()
}

static void testClosure() {
//    def foo  = BigInteger.&new
//    def fortyTwo = foo('42')
//    assert fortyTwo == 42G
    def action = this.&describe
    def list = [new Person(name: 'Bob', age: 42),
                new Person(name: 'Julia', age: 35)]
    assert transform(list, action) == ['Bob is 42', 'Julia is 35']
}

def transform(List elements, Closure action) {
    def result = []
    elements.each {
        result << action(it)
    }
    result
}

String describe(Person p) {
    "$p.name is $p.age"
}

static void testRegExp() {
    def reg = /.*test.*/
    def text = 'test hello'
    def matcher = text =~ reg
    println matcher.matches()
}

class Car {
    @Lazy
    String make
    String model
}

/**
 * 从对象中提取数据*/
static void testSpread() {
    def cars = [new Car(make: 'Peugeot', model: '508'),
                new Car(make: 'Renault', model: 'Clio')]
    def makes = cars*.make
    assert makes == ['Peugeot', 'Renault']
}

interface MyInterface {}