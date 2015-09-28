class Api::ApiController < ApplicationController
  #All controllers from api section will inherit from this controller
  #put common methods related to api here
  after_filter :cors_set_access_control_headers
  before_filter :cors_preflight_check
  # For all responses in this controller, return the CORS access control headers.

    def cors_set_access_control_headers
        headers['Access-Control-Allow-Origin'] = '*'
        headers['Access-Control-Allow-Methods'] = 'POST, PUT, DELETE, GET, OPTIONS'
        headers['Access-Control-Request-Method'] = '*'
        headers['Access-Control-Allow-Headers'] = 'Origin, X-Requested-With, Content-Type, Accept, Authorization'
    end

    def cors_preflight_check
        if request.method == :options
                 headers['Access-Control-Allow-Origin'] = '*'
                 headers['Access-Control-Allow-Methods'] = 'POST, GET, OPTIONS'
                 headers['Access-Control-Allow-Headers'] = '*'
                 headers['Access-Control-Max-Age'] = '1728000'
        end
    end
end
