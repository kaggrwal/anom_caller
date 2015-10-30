class Api::V1::OtpController < Api::ApiController

       skip_before_filter :authenticate_user!, :only => [:index]
       
       require_relative 'SHAgen.rb'
       @contact_no =""	        

 	
       def sha1
                @contact_no = params[:OTP][:c_no] #extracting contact no from :form
                _temp = @contact_no[0..4]
	    	_random = rand(100000..999990) #random generation
                _temp = _temp.to_i(2) + _random + @contact_no.to_i(2) #OTP generation for specific contact no
                
                _otp_code = _temp
                       
                @SHA1 = SHA_gen.new(_otp_code.to_s(2))
                _SHA =  @SHA1.getMsgDigest #get SHA for that OTP
		respond_to do |format|
                  format.json {render json: "OTP:#{_otp_code} SHA:#{_SHA}"} #return SHA
		end
      end
   
     
     
      
      def index
      end
end
