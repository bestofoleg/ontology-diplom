import catcher.annotation.TryToResolve;
import catcher.annotation.TryToResolveMethod;

@TryToResolve
public interface ITestClass {
    @TryToResolveMethod
    void test(SomeParent parent) throws NullPointerException;
}
