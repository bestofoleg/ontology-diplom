import catcher.annotation.TryToResolve;
import catcher.annotation.TryToResolveMethod;

@TryToResolve
public class TestClass implements ITestClass{
    @Override
    @TryToResolveMethod
    public void test(SomeParent parent) throws NullPointerException {
        System.out.println(parent.getVal());
    }

    public TestClass() {}
}
