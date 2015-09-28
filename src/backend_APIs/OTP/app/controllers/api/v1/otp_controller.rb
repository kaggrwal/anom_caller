class Api::V1::OtpController < Api::ApiController

	skip_before_filter :authenticate_user!, :only => [:index]

	def index
		otp_encrypt_send = {}
		otp_code = rand(100000..1000000)
		respond_to do |format|
	  	  format.json {render json: otp_code}
		end
	end
end
