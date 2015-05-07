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
		else if(viewName.equals("NewUserView") == true)
		{
			return new UserView(model, "InsertUsers" );
		}
        else if(viewName.equals("NewWorkerView") == true)
        {
            return new WorkerView(model, "AddWorkerTitle");
        }
        else if(viewName.equals("NewVehicleView") == true)
        {
            return new VehicleView(model,"AddVehicleTitle" );
        }
        else if(viewName.equals("RentalView")){
            return new RentalView(model);
        }
        else if(viewName.equals("RentalCollectionView")){
            return new RentalCollectionView(model);
        }
        else if(viewName.equals("UserSearchView")){
            return new SearchView(model,"User");
        }
        else if(viewName.equals("WorkerSearchView")){
            return new SearchView(model,"Worker");
        }
        else if(viewName.equals("BikeSearchView")){
            return new SearchView(model,"Vehicle");
        }
        else if(viewName.equals("ModifyUserView") == true){
            return new UserView(model, "ModifyUsers" );
        }
        else if(viewName.equals("ModifyWorkerView") == true){
            return new WorkerView(model, "ModifyWorker");
        }
        else if(viewName.equals("ModifyVehicleView") == true) {
            return new VehicleView(model, "ModifyVehicle");
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
