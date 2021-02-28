<?php 

include_once "models/users.php";

 
 class methods{     
     
    public function select()
    {
        $obj = new Users();
        $data = $obj->select();
        responseHandler::respond($data);

    }

    public function update(){
        $obj = new Users();

        if(requestHandler::get()!=null){
            $posts = requestHandler::get();
            $data = $obj->update($posts);
        }else{
            $data = array('update'=>false, "no values to update");
        }        
        responseHandler::respond($data);
    }


    public function insert(){
        $obj = new Users();

        if(requestHandler::get()!=null){
            $posts = requestHandler::get();
            $data = $obj->insert($posts);
        }else{
            $data = array('insert'=>false, "no values to insert");
        }
        
        responseHandler::respond($data);
    }


    public function delete(){
        $obj = new Users();

        if(requestHandler::get()!=null){
            $posts = requestHandler::get();
            $data = $obj->delete($posts);
        }else{
            $data = array('delete'=>false, "no id to use for delete");
        }
                        
        responseHandler::respond($data);
    }

 }  

 