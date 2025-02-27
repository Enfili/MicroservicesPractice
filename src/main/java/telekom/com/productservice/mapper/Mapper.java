package telekom.com.productservice.mapper;

public interface Mapper<A, B> {

    A mapFrom(B b);

    B mapTo(A a);
}
