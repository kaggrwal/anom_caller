
#File-: SHAgen.rb
#date-: 15th, october 2015
#reference-:http://m.metamorphosite.com/one-way-hash-encryption-sha1-data-software
#Author-:  Kumar Aggarwal
#Project-: anomCaller
#module-: OTP 

#format in code
 #functions in camel case(aB)
 #variables are underscore separated(a_b)



#initialization of Hash vaiables
$h0 = "01100111010001010010001100000001"
$h1 = "11101111110011011010101110001001"
$h2 = "10011000101110101101110011111110"
$h3 = "00010000001100100101010001110110"
$h4 = "11000011110100101110000111110000"



# return statements
FAILURE = 1
SUCCESS = 0


class SHA_gen
#class started 
 
 @msg_digest = nil  
 @input_string = nil
 @padded_message = nil
 

 def initialize(input)
   #default constructor call
   #input string from OTP_controller
   @input_string = input
   @words = [80] #list for expanded 80 words
   @K = nil #variable for addition in each hash function 
   #puts @input_string
   digestFormation(@input_string) #first call for function
 end
 
 def digestFormation(input)
   @binary_split = [] #array to hold the splitted input into binary format

   if(splitToBinary(input,@binary_split)) #splitbinary called 
     #puts "distengerated"
     
     @padded_message = paddingOfMessage(@binary_split) #message padding is done
     preProcessing(@padded_message) 
     wordExpansion()
    # _temp = binaryAdd("11101110000010111011001011000010110","10110111011010010100111100111110") 
     #puts "Add: #{_temp}"
     mainProcessing()  
     @msg_digest = retrvMsgDigest()
     return SUCCESS
   else
     puts "Failure in disintegeration"
     return FAILURE
   end
 end
 
 def getMsgDigest()
   # return final message digest formed
  return @msg_digest
 end
 
 def retrvMsgDigest()
  
    # message digest is formed from final hash variables 
   _temp = ""
   _temp = "%08x"% $h0.to_i(2) #hash varibales are converted to hex ....
   _temp = _temp + "%08x" % $h1.to_i(2)
   _temp = _temp + "%08x" % $h2.to_i(2)
   _temp = _temp + "%08x" % $h3.to_i(2)
   _temp = _temp + "%08x" % $h4.to_i(2)
   return _temp
 end
 
 def mainProcessing()
 
   #main processing is done here 
    
   _A = $h0
   _B = $h1
   _C = $h2
   _D = $h3
   _E = $h4 
   _k = 0


    #main loop started 
    while (_k<80)
    #puts "words #{_k}: #{@words[_k]}"  
    _temp2 = _A[5..31]+_A[0..4]  #A left rotate 5
    #puts _A 
    #puts _temp2 
    _temp = binaryAdd(_temp2,processFunc(_k,_B,_C,_D)) # ALROT5 + Function
    #puts "A+F: #{_temp}"
    _temp = binaryAdd(_temp,_E) #ALROT5+ Function + E
    #puts "A+F+E: #{_temp}"
    _temp = binaryAdd(_temp ,@K) #ALROT5+ Function + E + K 
    #puts "A+F+E+K: #{_temp}"
    _temp = binaryAdd(_temp, @words[_k])
    #puts "A+F+E+K+W: #{_temp}"
    _temp = trunc32(_temp) #truncated
    #puts "trunc: #{_temp}"
    _E = _D #chaging values ....
    _D = _C
    _C = _B[30..31]+_B[0..29]
    #puts _C
    _B = _A
    #puts _B
    _A = _temp
    _k = _k+1
   end
   
   $h0 = binaryAdd($h0,_A) #A+H0
   $h0 = trunc32($h0) 
   puts $h0
   $h1 = binaryAdd($h1,_B) #B+H1
   $h1 = trunc32($h1)
   puts $h1
   $h2 = binaryAdd($h2,_C) #c+H2
   $h2 = trunc32($h2)
   puts $h2
   $h3 = binaryAdd($h3,_D) #D+H3
   $h3 = trunc32($h3)
   puts $h3
   $h4 = binaryAdd($h4,_E) #E+h4
   $h4 = trunc32($h4)
   puts $h4
 end

 def trunc32(input)
    #function to truncate the binary string to 32 bits
 
   _size = input.size
   #puts _size
   _size1 = _size-32
   input = input[_size1.._size]
   return input 
 end  

 def processFunc(wno,b,c,d)

    #process Function or simply Function that returns computation on B, C, D.
   if(wno >= 0 && wno <= 19)
      #puts "b: #{b}"
      #puts "c: #{c}"
     _temp = bitAND(b,c)
     b = bitNOT(b)
     _temp2 = bitAND(b,d)
     _temp = bitOR(_temp,_temp2)
     @K = "01011010100000100111100110011001"
     return _temp
   elsif(wno >=20 && wno <=39)
     _temp = bitXOR(b,c)
     _temp = bitXOR(_temp,d)
     @K = "01101110110110011110101110100001"
     return _temp
   elsif(wno >=40 && wno <=59)
     _temp = bitAND(b,c)
     _temp2 = bitAND(b,d)
     _temp3 = bitAND(c,d)
     _temp = bitOR(_temp,_temp2)
     _temp = bitOR(_temp,_temp3)
     @K = "10001111000110111011110011011100"
     return _temp
   else
     _temp = bitXOR(b,c)
     _temp = bitXOR(_temp,d)
     @K = "11001010011000101100000111010110"
     return _temp
   end
 end     
   

 def binaryAdd(input1,input2)

    #function to return binary addition bit by bit
   _str_len1 = input1.size
   _str_len2 = input2.size
   _carry = 0
   _temp = [_str_len1]
   while(_str_len1 > 0 && _str_len2 > 0)  #looping till one string ends
     #puts _str_len1
     _temp2 = 0
     if(1 == input1[_str_len1-1].to_i(2)  && 1 == input2[_str_len2-1].to_i(2)) #when both have 1 in input
       if(1 == _carry)
         _carry = 1
         _temp2 = 1
       else
         _carry = 1
         _temp2 = 0
       end
     elsif((1 == input1[_str_len1-1].to_i(2) && 0 == input2[_str_len2-1].to_i(2)) || (0 == input1[_str_len1-1].to_i(2) && 1 == input2[_str_len2-1].to_i(2))) #either one having 0 and another 1
       if(1 == _carry)
         _carry = 1
         _temp2 = 0
       else
         _carry = 0
         _temp2 = 1
       end
     else #both having 0
       if(1 == _carry)
         _carry = 0
         _temp2 = 1
       else
         _carry = 0
         _temp2 = 0
       end
     end
       #puts "input1: #{input1[_str_len1-1].to_i(2)} + input2: #{input2[_str_len2-1].to_i(2)} temp2: #{_temp2} carry: #{_carry}"
       _temp[_str_len1] = _temp2.to_s(2)
       _str_len1  = _str_len1-1
       _str_len2  = _str_len2-1
   end
   while( _str_len1 > 0)
     #puts"str_len: #{_str_len1}"  #putting rest string with carries
     if(1 ==_carry)
       if(0 == input1[_str_len1-1].to_i(2))
         _temp2 = 1
         _carry = 0
       else
         _temp2 = 0
         _carry = 1
       end
     else
       if(0 == input1[_str_len1-1].to_i(2))
         _temp2 = 0
         _carry = 0
       else
         _temp2 = 1
         _carry = 0
       end  
     end
     _temp[_str_len1] = _temp2.to_s(2)
     _str_len1  = _str_len1-1
   end 
   _temp[_str_len1] = "1"
   return _temp.join("")
 end 
 
 def wordExpansion()

    #expanding the 16 word list to 80 words
   _j=16
   _temp=0
   while(_j<80)
      _temp = bitXOR(@words[_j-3],@words[_j-8])
      #puts "first: #{_temp}"
      _temp = bitXOR(_temp,@words[_j-14])
      #puts "second: #{_temp}"
      _temp = bitXOR(_temp,@words[_j-16])
      #puts "third: #{_temp}"
      _temp = _temp[1..31]+_temp[0]
     # _temp = asciiToBinary(32,_temp)
      @words[_j] = _temp
      #puts "words: #{_j} : #{@words[_j]}"
      _j = _j+1
   end
 end

 def bitXOR(input1,input2)

   #bitXOR operation bit by bit
   _str_len = input1.size
   _temp=""
   _k = 0
   while(_k != _str_len)
     _temp2 = input1[_k].to_i(2) ^ input2[_k].to_i(2)
     _temp2 =_temp2.to_s(2)
     #puts _temp2
     _temp[_k] =  _temp2
     _k = _k+1;
   end
   #puts "ret: #{_temp}"
   return _temp
 end 

 def bitOR(input1,input2)

    #bitOR operation bit by bit
   _str_len = input1.size
   _temp=""
   _k = 0
   while(_k != _str_len)
     _temp2 = input1[_k].to_i(2) | input2[_k].to_i(2)
     _temp2 =_temp2.to_s(2)
     #puts _temp2
     _temp[_k] =  _temp2
     _k = _k+1;
   end
   #puts "ret: #{_temp}"
   return _temp
 end

 def bitAND(input1,input2)

   #bitAND operation bit by bit
  _str_len = input1.size
  _temp=""
  _k = 0
  while(_k != _str_len)
     _temp2 = input1[_k].to_i(2) & input2[_k].to_i(2)
     _temp2 =_temp2.to_s(2)
     #puts _temp2
     _temp[_k] =  _temp2
     _k = _k+1;
  end
   #puts "ret: #{_temp}"
   return _temp
 end

 def bitNOT(input)

   #bitNOT operation bit by bit
  _str_len = input.size
  _temp =""
  _k = 0
  while(_k != _str_len)
     if(1 == input[_k].to_i(2))
       _temp[_k] = "0"
     else
       _temp[_k] = "1"
     end
    _k =_k+1
  end
  return _temp
 end


 def preProcessing(padded_message)

     #pre processing on padded message, forming 16 words list
    _count = 0
    _str_len = padded_message.size
    _i = 0
    while (_i < _str_len)
      @words[_count] =  padded_message[_i.._i+32-1]
      #puts "words: #{_count} : #{@words[_count]}"
      _count = _count+1
      _i = _i+32
    end   
 end

 def splitToBinary(input,splitted_arr)

   #processing each character of input to ASCII to binary in an array
   input.each_byte do |c|
     @element = asciiToBinary(8,c)
     splitted_arr << @element     
   end
 end

 def paddingOfMessage(splitted_arr)
  
      #padding of message 1 +  to form 448 equivalent + length in 64 bits
     
     _temp = splitted_arr.join("")
     _orig_len = _temp.size
     _temp = _temp+"1"
     _str_len = _orig_len+1
     if (_str_len<448)
       _no_0s = 448-_str_len
       for i in 1.._no_0s
        _temp = _temp+"0"
       end 
     else
      _no_0s = 960-_str_len
      for i in 1.._no_0s
       _temp = _temp+"0"
      end
     end
     _orig_len = asciiToBinary(64,_orig_len)
     _temp = _temp+_orig_len.to_s
     
    return _temp   
     
 end

 def asciiToBinary(width,ascii)

    #function to convert ascii to binary 
   _temp = '%0*b' % [width, ascii]
   return _temp
 end


#class SHAGEN end
end
