import kotlin.math.abs
import kotlin.math.sqrt

fun main(args: Array<String>)
{
    while(true)
    {
        println("Домашняя работа Масловой Т. по теме Классы. Введите номер задания 1, 2 или 3, или любую клавишу для выхода")
        val act = readln()
        if (act == "1") main1()
        else if (act == "2") println("3 задание включает в себя решение 2, поэтому смотрите задание 3.")
        else if (act == "3") main3()
        else
        {
            println("Выход")
            break
        }
    }
}

fun main1()
{
    var found = false
    var toFind = Point()
    var ABC = Triangle()

    //toFind.x = 2.0; toFind.y = 2.0; //is in
  // toFind.x = 2.0; toFind.y = 1.0; //is on
   // toFind.x = 1.0; toFind.y = 1.0; //is
   // toFind.x = 4.0; toFind.y = 4.0; //is out
  //  ABC.a.x = 1.0; ABC.a.y = 1.0;
 //  ABC.b.x = 2.0; ABC.b.y = 4.0;
   // ABC.c.x = 4.0; ABC.c.y = 1.0;

    println("Я ищу, в треугольнике ABC ли находится точка D");
    println("Введите координату X точки D:")
    toFind.x=readln().toDouble()
    println("Введите координату Y точки D:")
    toFind.y=readln().toDouble()

    println("Введите координату X точки A:")
    ABC.a.x=readln().toDouble()
    println("Введите координату Y точки A:")
    ABC.a.y=readln().toDouble()

    println("Введите координату X точки B:")
    ABC.b.x=readln().toDouble()
    println("Введите координату Y точки B:")
    ABC.b.y=readln().toDouble()

    println("Введите координату X точки C:")
    ABC.c.x=readln().toDouble()
    println("Введите координату Y точки C:")
    ABC.c.y=readln().toDouble()

    //check if point is equal to any of the triangle's
    if (toFind.Equals(ABC.a)|| toFind.Equals(ABC.b) || toFind.Equals(ABC.b))
    {
        println("Точка находится на границе треугольника. " +
                "\nА именно в одной из его вершин.")
        found = true
    }
    //check if point is on a side
    else if (toFind.OnLine(ABC.a,ABC.b) || toFind.OnLine(ABC.c, ABC.b) || toFind.OnLine(ABC.a, ABC.c) )
    {
        println("Точка находится на границе треугольника. ")
        found = true
    }
    //get all triangles
    //get all triangles
    val ABCarea = ABC.Area()
    val ABd: Double = Triangle.Area(ABC.a, ABC.b, toFind)
    val ACd: Double = Triangle.Area(ABC.a, ABC.c, toFind)
    val BCd: Double = Triangle.Area(ABC.b, ABC.c, toFind)

    //check if point is inside: sum is equal to ABC
    if (ABCarea==(ABd+ACd+BCd) && !found)
    {
        println("Точка находится внутри треугольника");
    }
    else if (!found)
    {
        println("Точка находится внe треугольника");
    }
}

fun main3()
{
    /*
    1;1 and 4;5 = 5
    2;3 and 3;3 = 1
     */
    var stub=Point1(0.0,0.0)
    println("Поиск наименьшего и наибольшего расстояния между точками из набора")
    println("Введите количество точек: ")
    var n = readln().toInt()//array length
    if (n<2) n=2
    val arr : MutableList<Point1> = ArrayList()
    for (i in 0..n-1)
    {
        var tmp=i+1//output
        println("Введите координату X точки А$tmp: ")
        var tmp1= readln().toDouble()
        println("Введите координату Y точки А$tmp: ")
        var tmp2 = readln().toDouble()
        arr.add(i,Point1(tmp1,tmp2))
    }
    var tmp1=stub.BiggestDistance(arr)
    var tmp2=stub.SmallestDistance(arr)
    println("Наибольшее расстояние - $tmp1")
    println("Наименьшее расстояние - $tmp2")
}

//delta of a 3*3 matrix
fun Det(a11: Double,a12: Double,a13: Double,a21: Double,a22: Double,a23: Double,a31: Double,a32: Double,a33: Double):Double
{
    return a11 * a22 * a33 - a11 * a23 * a32 - a12 * a21 * a33 + a12 * a23 * a31 + a13 * a21 * a32 - a13 * a22 * a31
}
class Point
{
    public var x = 0.0 // ----------x
    public var y = 0.0 // |y

    init//base constructor
    {
        x = 0.0
        y = 0.0
    }
    fun OnLine(lineSt: Point, lineEnd: Point):Boolean //whether is a point on a line
    {
        //horizontal
        if (lineSt.x == lineEnd.x)
            if (lineEnd.x == x)
                if (y < lineSt.y && y > lineEnd.y || y > lineSt.y && y < lineEnd.y) {return true}
        //vertical
        if (lineSt.y == lineEnd.y)
            if (lineEnd.y == y)
                if (x < lineSt.x && x > lineEnd.x || x > lineSt.x && x < lineEnd.x) {return true}

        //(x-x1)/(x2-x1)=(y-y1)/(y2-y1)
        var tmp1=(lineEnd.x - lineSt.x)
        var tmp2=(lineEnd.y - lineSt.y)
        if (tmp1==0.0 || tmp2==0.0)
        {
            return if (tmp1==tmp2) true else false
        }
        else
        {
            val halfX = (x - lineSt.x) / tmp1
            val halfY = (y - lineSt.y) / tmp2
            return if (halfX == halfY) true else false
        }
    }
    fun Equals(a: Point):Boolean //if 2 points are the same
    {
        return if (a.x == x && a.y == y) true else false
    }
}

class Triangle
{
    var a: Point
    var b: Point
    var c: Point

    init //base constructor
    {
        a = Point()
        b = Point()
        c = Point()
    }
    companion object //static
    {
    fun Area(A: Point, B: Point , C: Point): Double //find from 3 points
    {
            //S = 0.5[(x1-x3)(y2-y3)-(x2-x3)(y1-y3)]
            var a = 0.5 * abs(Det(A.x, A.y, 1.0, B.x, B.y, 1.0, C.x, C.y, 1.0))
            return(a)
    }
    }
    fun Area(): Double
    {
        return Area(a,b,c)
    }
}
class Point1 //for task 3
{
    var x = 0.0 // ----------x
    var y = 0.0 // |y
    init
    {
        x=0.0;y=0.0
    }
    constructor(x_:Double,y_:Double)
    {
        x=x_;y=y_
    }
    fun Distance(a: Point1, b: Point1): Double //distance between 2 points
    {
        return sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y))
    }
    fun Equals(a: Point1): Boolean //if 2 points are the same
    {
        return if (a.x == x && a.y == y) true else false
    }

    fun BiggestDistance(arr: MutableList<Point1>): Double //find biggest
    {
        var max = Distance(arr[0], arr[1])
        var temp = 0.0
        for (i in 0 .. arr.size-1) for (j in 0 .. arr.size-1) {
            temp = Distance(arr[i], arr[j])
            if (temp > max && temp > 0) max = temp
        }
        return max
    }
    fun SmallestDistance(arr: MutableList<Point1>): Double //find smallest
    {
        var min = Distance(arr[0], arr[1])
        var temp = 0.0
        for (i in 0 .. arr.size-1) for (j in 0 .. arr.size-1) {
            temp = Distance(arr[i], arr[j])
            if (temp < min && temp > 0) min = temp
        }
        return min
    }
}


