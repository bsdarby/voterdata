@FunctionalInterface
interface Converter<F, T> {
Converter<String, Integer> converter = (from) -> Integer.valueOf(from);


Integer converted = converter.convert("123");

	T convert(F from);
System.out.println(converted);

}