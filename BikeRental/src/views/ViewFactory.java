package views;

import impres.impresario.IModel;

//==============================================================================
public class ViewFactory {

    public static View createView(String viewName, IModel model)
    {
        switch (viewName) {
            case "LoginView":
                return new LoginView(model);
            case "WorkerView":
                return new WorkerView(model);
            case "BikeTransactionChoiceView":
                return new BikeTransactionChoiceView(model);
            case "UserView":
                return new UserView(model);
            case "VehicleView":
                return new VehicleView(model);
            case "RentalCollectionView":
                return new RentalCollectionView(model);
            default:
                return null;
        }
//        if (viewName.equals("LoginView"))
//            return new LoginView(model);
//        else if (viewName.equals("WorkerView"))
//            return new WorkerView(model);
//        else if (viewName.equals("BikeTransactionChoiceView"))
//            return new BikeTransactionChoiceView(model);
//		else if (viewName.equals("UserView"))
//			return new UserView(model);
//        else if (viewName.equals("VehicleView"))
//            return new VehicleView(model);
//        else if (viewName.equals("RentalView"))
//            return new RentalView(model);
//        else if(viewName.equals("RentalCollectionView"))
//            return new RentalCollectionView(model);
//        // else if(viewName.equals("SearchUserView")){
//        //     return new SearchView(model,"User");
//        // }
//        // else if(viewName.equals("SearchWorkerView")){
//        //     return new SearchView(model,"Worker");
//        // }
//        // else if(viewName.equals("SearchBikeView")){
//        //     return new SearchView(model,"Bike");
//        // }
//        else
//            return null;
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
