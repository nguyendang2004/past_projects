
import sys
from datetime import datetime
dictionary3 = {}
dictionary2 = {}
zug = 'y'
 
def UTILITY(state, filled):
    #ours
    if compute_4_position(state):
        return 10000
    #enemy
    if compute_4_position(state^filled):
        return -10000
    return 0

def compute_4_position(position) :
    score =0
    #vertical
    #r = (position << 1) & (position << 2) & (position << 3)
    r = (position << 10) & (position << 20) & (position << 30)&position
    if r:
        return 1
    #horizontal
    r=  (position << 1) & (position << 2) & (position << 3)&position
    if r:
        return 1
    #diagonal 1
    r=  (position << 11) & (position << 22) & (position << 33)&position
    if r:
        return 1
    #diagonal 2
    r=  (position << 9) & (position << 18) & (position << 27)&position
    if r:
        return 1
    return 0

def compute_2_position(position,next_possible) :
    score =0

    if position in dictionary2:
        r = dictionary2[position]
        return r&next_possible

    #vertical
    #r = (position << 1) & (position << 2) & (position << 3)
    r = (position << 10) & (position << 20) 

    #horizontal
    p = (position << 1) 
    r |= p & (position << 2)
    r |= p & (position >>  1)
    p = (position >> 1) 
    r |= p & (position << 1)
    r |= p & (position >> 2 * 1)

    p = (position << 1) 
    r |= p & (position << 3)
    r |= p & (position >>  2)
    p = (position >> 1)
    r |= p & (position << 2)
    r |= p & (position >> 3 * 1)

    p = (position << 1) 
    r |= p & (position << 3)
    p = (position >> 1)
    r |= p & (position >> 3 * 1)
    #print(bin(r))



    #diagonal 1
    p = (position << 11) 
    r |= p & (position << 2 *11)
    r |= p & (position >> 11)
    p = (position >> 11)
    r |= p & (position << 11)
    r |= p & (position >> 2 * 11)
    #print(bin(r))
    p = (position << 11) 
    r |= p & (position >>2*11)
    p = (position >> 11)
    r |= p & (position << 2*11)

    p = (position << 11) 
    r |= p & (position << 3*11)
    p = (position >> 11)
    r |= p & (position >> 3 * 11)

    

    #diagonal 2
    p = (position << 9)
    r |= p & (position << 2 * 9)
    r |= p & (position >> 9)
    p = (position >> 9) 
    r |= p & (position << 9)
    r |= p & (position >> 2 * 9)

    p = (position << 9) 
    r |= p & (position >>18)
    p = (position >> 9)
    r |= p & (position << 18)

    p = (position << 9) 
    r |= p & (position << 3*9)
    p = (position >> 9)
    r |= p & (position >> 3 * 9)

    dictionary2[position] = r
    mask = 0b111000000011100000001110000000111000000011100000001110000000
    if (position&mask==0 and next_possible&mask==0 and r&next_possible&mask!=0):
        print("jisne2")
        sys.exit()
    return r&next_possible

def find_possible_move(filled):
    mask = 0b111000000011100000001110000000111000000011100000001110000000

    h = filled <<10
    #bottom = 0b1111111
    h=h|127
    if (filled&mask==0 and (filled^h)&mask!=0):
        print("jisne")
        print(bin(filled^h))
        sys.exit()

    return filled^h

def compute_3_position(position, next_possible) :
    if position in dictionary3:
        r = dictionary3[position]
        return r&next_possible
    #vertical
    #r = (position << 1) & (position << 2) & (position << 3)
    r = (position << 10) & (position << 20) & (position << 30)

    #horizontal
    p = (position << 1) & (position << 2)
    r |= p & (position << 3)
    r |= p & (position >>  1)
    p = (position >> 1) & (position >> 2 * 1)
    r |= p & (position << 1)
    r |= p & (position >> 3 * 1)

    #print(bin(r))

    #diagonal 1
    p = (position << 11) & (position << 2*11)
    r |= p & (position << 3 * 11)
    r |= p & (position >> 11)
    p = (position >> 11) & (position >> 2*11)
    r |= p & (position << 11)
    r |= p & (position >> 3 * 11)
    #print(bin(r))
#

    #diagonal 2
    p = (position << 9) & (position << 2 * 9)
    r |= p & (position << 3 * 9)
    r |= p & (position >> 9)
    p = (position >> 9) & (position >> 2 *9)
    r |= p & (position << 9)
    r |= p & (position >> 3 * 9)

    dictionary3[position] = r
    mask = 0b111000000011100000001110000000111000000011100000001110000000
    if (position&mask==0 and next_possible&mask==0 and r&next_possible&mask!=0):
        print("jisne3")
        sys.exit()
    if (r&next_possible):
        print("feo")
        print(bin(position))
        print(bin(r))
        print(bin(next_possible))
    return r&next_possible

def sum_board(board):
    count = 0
    while board:
        count += (board & 1)
        board >>= 1
    return count

def SCORE(filled, position, turn, initial_turn):
    possible_move = find_possible_move(filled)
    opponent_position = position ^ filled
    threat_3 = compute_3_position(opponent_position, possible_move)
    threat_2 = compute_2_position(opponent_position, possible_move)
    opp_3 =  compute_3_position(position, possible_move) #immediate win next turn
    opp_2 = compute_2_position(position,possible_move) # minor threats
    mask = 0b111000000011100000001110000000111000000011100000001110000000
    if (opp_2&mask):
        print(opp_2)
        print(bin(position))
        print(bin(possible_move))
        print("no1")
        sys.exit()
    if opp_3&mask:
        print(opp_3)
        print("no2")
        sys.exit()
    if threat_3&mask:
        print(threat_3)
        print("no3")
        sys.exit()
    if threat_2&mask:
        print(threat_2)
        print("no4")
        sys.exit()
    our_turn = turn==initial_turn
    zug = "r" if 139638109896831 and threat_3 else "y"

    print(bin(opp_3))
    if opp_3 and our_turn:
        return 1000 #instant win
    elif threat_3 and not our_turn:
        return -1000

    # Apply rules if we have zugzwang
    if zug == initial_turn and not our_turn:
        # -- 3-threat --
        # Claimeven
        # All even squares = 0b000111111100000000000001111111000000000000011111110000000000
        # = 142989424534354944
        claimeven = threat_3 & 142989424534354944 & ~(filled<<10)
        
        # Aftereven
        # Columns: 1127000493261825, 2254000986523650, 4508001973047300, 9016003946094600,
        #          18032007892189200, 36064015784378400, 72128031568756800
        # Filled columns: 1127000493260800 | 1126999418470400, 
        #                 2254000986521600 | 2253998836940800,
        #                 4508001973043200 | 4507997673881600,
        #                 9016003946086400 | 9015995347763200,
        #                 18032007892172800 | 18031990695526400,
        #                 36064015784345600 | 36063981391052800,
        #                 72128031568691200 | 72127962782105600
        # Second row = 130048
        # Fourth row = 136365211648
        aftereven = 1127000493260800 if (1127000493261825 & (opp_3 & 130048)) \
                                     else (1126999418470400 
                                           if (1127000493261825 & (opp_3 & 136365211648))
                                           else 0)
        aftereven |= 2254000986521600 if (2254000986523650 & (opp_3 & 130048)) \
                                      else (2253998836940800 
                                           if (2254000986523650 & (opp_3 & 136365211648))
                                           else 0)
        aftereven |= 4508001973043200 if (4508001973047300 & (opp_3 & 130048)) \
                                      else (4507997673881600 
                                           if (4508001973047300 & (opp_3 & 136365211648))
                                           else 0) 
        aftereven |= 9016003946086400 if (9016003946094600 & (opp_3 & 130048)) \
                                      else (9015995347763200 
                                           if (9016003946094600 & (opp_3 & 136365211648))
                                           else 0)
        aftereven |= 18032007892172800 if (18032007892189200 & (opp_3 & 130048)) \
                                       else (18031990695526400 
                                           if (18032007892189200 & (opp_3 & 136365211648))
                                           else 0)
        aftereven |= 36064015784345600 if (36064015784378400 & (opp_3 & 130048)) \
                                       else (36063981391052800 
                                           if (36064015784378400 & (opp_3 & 136365211648))
                                           else 0)
        aftereven |= 72128031568691200 if (72128031568756800 & (opp_3 & 130048)) \
                                       else (72057594037927936 
                                           if (72127962782105600 & (opp_3 & 136365211648))
                                           else 0)
        aftereven &= threat_3

        # Before 
        # ~Top row = 0b000000000000011111110001111111000111111100011111110001111111
        # = 139774475238527
        before = ((opp_3 << 10) & threat_3) & 139774475238527

        # Combine
        threat_3 ^= (claimeven | aftereven | before)

        # -- 2-threat --
        # Naive claimeven
        claimeven = threat_2 & 142989424534354944 & ~(filled<<10)
        claimeven = ((claimeven<<1 & claimeven) | (claimeven>>1 & claimeven))

        # Naive baseinverse
        baseinverse = threat_2 & possible_move
        baseinverse = ((baseinverse<<1 & baseinverse) | (baseinverse>>1 & baseinverse))

        # Vertical
        # All odd squares = 0b000000000000011111110000000000000111111100000000000001111111
        # = 139638109896831
        vertical = (threat_2 & threat_2<<10 & 139638109896831) \
                   | (threat_2 & threat_2>>10 & 142989424534354944)
        
        # Naive aftereven
        aftereven = 1127000493260800 if (1127000493261825 & (opp_2 & 130048)) \
                                     else (1126999418470400 
                                           if (1127000493261825 & (opp_2 & 136365211648))
                                           else 0)
        aftereven |= 2254000986521600 if (2254000986523650 & (opp_2 & 130048)) \
                                      else (2253998836940800 
                                           if (2254000986523650 & (opp_2 & 136365211648))
                                           else 0)
        aftereven |= 4508001973043200 if (4508001973047300 & (opp_2 & 130048)) \
                                      else (4507997673881600 
                                           if (4508001973047300 & (opp_2 & 136365211648))
                                           else 0) 
        aftereven |= 9016003946086400 if (9016003946094600 & (opp_2 & 130048)) \
                                      else (9015995347763200 
                                           if (9016003946094600 & (opp_2 & 136365211648))
                                           else 0)
        aftereven |= 18032007892172800 if (18032007892189200 & (opp_2 & 130048)) \
                                       else (18031990695526400 
                                           if (18032007892189200 & (opp_2 & 136365211648))
                                           else 0)
        aftereven |= 36064015784345600 if (36064015784378400 & (opp_2 & 130048)) \
                                       else (36063981391052800 
                                           if (36064015784378400 & (opp_2 & 136365211648))
                                           else 0)
        aftereven |= 72128031568691200 if (72128031568756800 & (opp_2 & 130048)) \
                                       else (72057594037927936 
                                           if (72127962782105600 & (opp_2 & 136365211648))
                                           else 0)
        aftereven &= threat_2
        aftereven = ((aftereven<<1 & aftereven) | (aftereven>>1 & aftereven))

        # Naive before
        before = ((opp_2 << 10) & threat_2) & 139774475238527
        before = ((before<<1 & before) | (before>>1 & before))

        # Combine
        neutralised = aftereven | before | claimeven | baseinverse
        threat_2 ^= neutralised if neutralised & vertical else neutralised | vertical

    return (sum_board(opp_2)*2 + sum_board(opp_3)*3) - (sum_board(threat_2)*2 + sum_board(threat_3)*3) + (zug == initial_turn)*2.5

#check zugswang
def have_zugzwang(filled, our_table):
    if 139638109896831 & compute_3_position(our_table, filled):
        return 1
    return 0

# Returns the (value, number of visits, index to play)
def alphaBeta(filled, our_board, turn, max_depth, alpha, beta, initial_turn):
    value = UTILITY(our_board, filled)
    next_possible = find_possible_move(filled)
    print(max_depth)

    # Base case
    if max_depth == 0 and value == 0:
        return (SCORE(filled, our_board, turn, initial_turn), 1, -1)
    #if depth =max-1 and oppo turn
    elif max_depth ==1 and turn != initial_turn and zug == initial_turn:
        
        return (SCORE(filled, our_board, turn, initial_turn), 1, -1)

    else:
        if next_possible == 0:
            if value == 0:
                value = SCORE(filled, our_board, next_possible, turn)
            return (value, 1, -1)
        visits = 1
        # create children
        #original_mask = 0b0001000000100000010000001000000100000010000001000#middle col
        original_mask = 0b0001000000000100000000010000000001000000000100000000010000000001000#middle col
        mask= original_mask

        #vlaue of smallest and largest
        store =10000
        store_index =0
        if turn =='r':
            store =-10000

        #evaluate them to see if they have zugswang
        for i in range(0, 6):
            
            move = mask&next_possible
            if (i%2 ==0):
                mask = original_mask <<(i//2 +1)
            else:
                mask = original_mask >>(i//2 +1)
            if alpha != None and beta != None:
                if (alpha >= beta):
                    break
            if (turn == initial_turn):
                our_board= our_board|move
            #switch board from red to yellow

            recurVal = alphaBeta(move|filled,our_board , "y" if turn=="r" else "r", max_depth-1, alpha, beta, initial_turn)
            print(recurVal)
            #ignore next children if gains immediate win for us
            if turn ==initial_turn and recurVal[0]==1000:
                return  (1000,visits + recurVal[1],i)


            # Update current alpha and beta
            if turn == "r" and (alpha == None or recurVal[0] > alpha):
                alpha = recurVal[0]
            elif turn == "y" and (beta == None or recurVal[0] < beta):
                beta = recurVal[0]
            visits += recurVal[1]
            if turn =='r':
                if store < recurVal[0]:
                    store =recurVal[0]
                    store_index = i
            else:
                if store > recurVal[0]:
                    store =recurVal[0]
                    store_index = i
        return (store, visits,store_index)

def connect_four_ab(contents, turn, max_depth):
    #turn contents into red and filled_board
    if turn == "red":
        turn = "r"
    elif turn == "yellow":
        turn = "y"

    filled, our_board, counter = 0, 0, 56
    for i in range(len(contents)-1, -1, -1):
        if contents[i]=='r' or contents[i]=='y':
            filled= filled|(1<<(56-counter))
        if contents[i]== turn:
            our_board =our_board|(1<<(56-counter))
        if contents[i] ==',':
            counter =counter-2
        counter -= 1
    
    print("start",bin(filled))
    print("start",bin(our_board))

    recurVal = alphaBeta(filled, our_board, turn, max_depth, None, None, turn)
    return recurVal[2]

if __name__ == '__main__':
    if len(sys.argv) <= 1:
        # You can modify these values to test your code
        board = '.......,.......,.......,.......,.......,......'
        player = 'red'
    else:
        board = sys.argv[1]
        player = sys.argv[2]
    start_time = datetime.now()
    print(connect_four_ab(board, player, 2))
    end_time = datetime.now()
    running_time = end_time - start_time

    print("Running time:", running_time)

