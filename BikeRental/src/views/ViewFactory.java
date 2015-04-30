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
        else if(viewName.equals("WorkerView") == true)
        {
            return new WorkerView(model);
        }
        else if(viewName.equals("BikeTransactionChoiceView") == true)
        {
            return new BikeTransactionChoiceView(model);
        }
		else if(viewName.equals("UserView") == true)
		{
			return new UserView(model);
		}
        else if(viewName.equals("VehicleView") == true)
        {
            return new VehicleView(model);
        }
        else if(viewName.equals("RentalView")){
            return new RentalView(model);
        }
        else if(viewName.equals("RentalCollectionView")){
            return new RentalCollectionView(model);
        }
        else if(viewName.equals("SearchUserView")){
            return new SearchView(model,"User");
        }
        else if(viewName.equals("SearchWorkerView")){
            return new SearchView(model,"Worker");
        }
        else if(viewName.equals("SearchBikeView")){
            return new SearchView(model,"Bike");
        }
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
