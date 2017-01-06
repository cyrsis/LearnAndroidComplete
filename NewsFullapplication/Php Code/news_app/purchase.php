<?php error_reporting(0);
/* 
	 * Item purchase verification by envato api
	 */
	function verify_purchase($buyer_name,$p_code)
	{
		 
		
		$envato_username = "viaviwebtech";
		$envato_api_key  = "2uw51mxa55u4dsxh8m8a4i7bqp4ux1p0";
		
		$buyer				=	$buyer_name;
		$purchase_code		=	$p_code;
		
		$curl 				=	curl_init('http://marketplace.envato.com/api/edge/'.$envato_username.'/'.$envato_api_key.'/verify-purchase:'.$purchase_code.'.xml');
		
		curl_setopt($curl, CURLOPT_USERAGENT, $_SERVER['HTTP_USER_AGENT'] );
		curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($curl, CURLOPT_TIMEOUT, 30);
		curl_setopt($curl, CURLOPT_FOLLOWLOCATION, 1);
		
		$purchase_data		=	curl_exec($curl);
		curl_close($curl);
		
		
		$purchase_data		=	json_decode(json_encode((array) simplexml_load_string($purchase_data)),1);

		if ( isset($purchase_data['verify-purchase']['buyer']) && $purchase_data['verify-purchase']['buyer'] == $buyer)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	function purchase_status()
	{
		$app_qry="SELECT * FROM app_verify WHERE id='1'";
		$app_result=mysql_query($app_qry);
		$app_row=mysql_fetch_assoc($app_result);
		
		if($app_row['status']=='1')
		{
			return true;
		}
		else
		{
			return false;
		}
	}
 
?>