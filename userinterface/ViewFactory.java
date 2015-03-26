package userinterface;

import impresario.IModel;

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
        /*
		else if(viewName.equals("UserView") == true)
		{
			return new UserView(model);
		}
		else if(viewName.equals("WorkerView") == true)
		{
			return new WorkerView(model);
		}
		else if(viewName.equals("VehicleView") == true)
		{
			return new VehicleView(model);
		}
		else if(viewName.equals("RentView") == true)
		{
			return new RentView(model);
		}
		else if(viewName.equals("ReturnView") == true)
		{
			return new ReturnView(model);
		}
		else if(viewName.equals("FindUser") == true)
		{
			return new FindUser(model);
		}
		else if(viewName.equals("FindWorker") == true)
		{
			return new FindWorker(model);
		}
		else if(viewName.equals("FindVehicle") == true)
		{
			return new FindVehicle(model);
		}
        */
        else
			return null;
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
