# DataGetter
Json数据请求

##使用方法
获取单例对象：

    DataGetter.INSTANCE;

使用前先进行初始化：

    DataGetter.INSTANCE.init(context.getApplicationContext());

>注意：这里需要使用applicationContext，否则会发生内存泄漏。
获取一个Json数组：

    DataGetter.INSTANCE.get(url, class, mActivity, mOnFinishListener);

>class为需要返回的对象的Class
 OnFinishListener为泛型接口，类型为需要返回的对象的类型。
 返回结果将装入ArrayList，如果返回结果为空则是一个空的ArrayList。
 回调会在UIThread中进行，不必再次转换到UIThread。

在不再使用之后建议关闭以释放资源：

    DataGetter.INSTANCE.close();

##自定义GettingOption
GettingOption使用Builder模式创建新的对象
比如通过https获取：

    GettingOption mOption = new Builder(URL).https().builder();
    DataGetter.INSTANCE.get(mOption, class, mActivity, mOnFinishListener);

