package views;

import impres.impresario.IModel;

//==============================================================================
public class ViewFactory {

    public static View createView(String viewName, IModel model)
    {
        if(viewName.equals("LoginView") == true)
        {
            return new LoginView(model);
        }
        else if(viewName.equals("BikeTransactionChoiceView") == true)
        {
            return new BikeTransactionChoiceView(model);
        }
		else if(viewName.equals("AddUser") == true)
		{
			return new UserView(model);
		}
        else
            throw new RuntimeException("Unknow view: " + viewName);
    }


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
